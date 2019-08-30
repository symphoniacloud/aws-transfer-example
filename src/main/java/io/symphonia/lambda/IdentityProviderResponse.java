package io.symphonia.lambda;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProviderResponse {

    private String role;
    private String[] publicKeys = new String[0];
    private String policy;
    private String homeDirectory;

    @JsonProperty("Role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("PublicKeys")
    public String[] getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(String[] publicKeys) {
        this.publicKeys = publicKeys;
    }

    @JsonProperty("Policy")
    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    @JsonProperty("HomeDirectory")
    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }
}
