# aws-transfer-poc

This project is an example of an AWS Transfer for SFTP endpoint using a custom identity provider.
The custom identity provider is API Gateway backed by a Java-based AWS Lambda function.

The Lambda function receives a request from the AWS Transfer service, and checks that the password provided matches a hard-coded value.
If the password matches, the Lambda function returns a valid response authorizing the user, and providing an IAM policy that limits the users access to their own home directory in a particular S3 bucket.

Users do *not* have to be created ahead of time in the AWS Transfer console (or in this project's SAM `template.yaml` file).

## Deploy

This project is deployed using the [Serverless Application Model](https://github.com/awslabs/serverless-application-model).

Your AWS credentials and region preference should be configured using the standard AWS CLI method, found here: https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html

The deploy script found in `bin/deploy.bash` takes a single argument, which is the name of the S3 bucket that SAM will use to stage uploaded Lambda artifacts.
This bucket must exist in the same region as the stack.

```bash
$ bin/deploy.bash <S3 bucket>
```
