package uk.gov.hmcts.reform.cmc.submit;

import uk.gov.hmcts.reform.idam.client.models.UserDetails;

public class User {

    public User(String authToken, UserDetails userDetails) {
        this.authToken = authToken;
        this.userDetails = userDetails;
    }

    private String authToken;

    private UserDetails userDetails;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

}
