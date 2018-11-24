package com.pldfodb.oauth;

import org.springframework.security.core.userdetails.UserDetails;

public abstract class OpenIdUserDetails implements UserDetails {

    private String userId;
    private String username;
//    private OAuth2AccessToken token;

//    public OpenIdUserDetails(Map<String, String> userInfo, OAuth2AccessToken token) {
//        this.userId = userInfo.get("sub");
//        this.username = userInfo.get("email");
//        this.token = token;
//    }
}
