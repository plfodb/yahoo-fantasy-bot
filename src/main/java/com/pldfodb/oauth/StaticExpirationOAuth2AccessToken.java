package com.pldfodb.oauth;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Calendar;
import java.util.Date;

public class StaticExpirationOAuth2AccessToken extends DefaultOAuth2AccessToken {

    public StaticExpirationOAuth2AccessToken(OAuth2AccessToken accessToken) {
        super(accessToken);
        Calendar calendar = Calendar.getInstance();
        super.setExpiration(calendar.getTime());
    }

    @Override
    protected void setExpiresIn(int delta) {
        // no op
    }

    @Override
    public void setExpiration(Date expiration) {
        // no op
    }
}
