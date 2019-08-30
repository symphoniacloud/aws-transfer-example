package io.symphonia.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;

import java.io.IOException;

public class IdentityProvider {

    private static String PASSWORD = "friend";
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AmazonS3 s3;
    private final String bucket;
    private final String roleArn;
    private final String userPolicy;

    public IdentityProvider() throws IOException {
        this(AmazonS3ClientBuilder.defaultClient());
    }

    public IdentityProvider(AmazonS3 s3) throws IOException {
        this.s3 = s3;
        this.bucket = System.getenv("BUCKET");
        this.roleArn = System.getenv("ROLE_ARN");
        this.userPolicy = IOUtils.toString(IdentityProvider.class.getResourceAsStream("/user-policy.json"));
    }

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) throws IOException {

        String serverId = request.getPathParameters().get("serverId");
        String userId = request.getPathParameters().get("userId");
        String password = request.getHeaders().get("Password");

        System.out.println(String.format("%s %s %s", serverId, userId, password));

        APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatus.SC_OK);

        // Just check the password
        if (PASSWORD.equalsIgnoreCase(password)) {

            // Create user "directory"
            s3.putObject(bucket, String.format("%s/", userId), "");

            IdentityProviderResponse idResponse = new IdentityProviderResponse();
            idResponse.setHomeDirectory(String.format("/%s/%s", bucket, userId));
            idResponse.setRole(roleArn);
            idResponse.setPolicy(userPolicy);

            String responseBody = OBJECT_MAPPER.writeValueAsString(idResponse);
            System.out.println(responseBody);

            apiResponse.setBody(responseBody);
        } else {
            System.out.println("Access denied");
        }

        return apiResponse;
    }

}