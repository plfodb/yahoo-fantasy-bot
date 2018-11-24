package com.pldfodb.oauth;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class ClientOnlyAuthorizationCodeDetails extends AuthorizationCodeResourceDetails {

    public boolean isClientOnly() {
        return true;
    }
}
