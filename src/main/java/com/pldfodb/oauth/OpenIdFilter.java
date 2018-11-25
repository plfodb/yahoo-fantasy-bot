package com.pldfodb.oauth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIdFilter extends AbstractAuthenticationProcessingFilter {

    public OpenIdFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(new NoopAuthenticationManager());
    }
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        OAuth2AccessToken accessToken;
//        try {
//            accessToken = restTemplate.login();
//        } catch (OAuth2Exception e) {
//            throw new BadCredentialsException("Could not obtain access token", e);
//        }
//        try {
//            String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
//            String kid = JwtHelper.headers(idToken).get("kid");
//            Jwt tokenDecoded = JwtHelper.decodeAndVerify(idToken, verifier(kid));
//            Map<String, String> authInfo = new ObjectMapper()
//                    .readValue(tokenDecoded.getClaims(), Map.class);
//            verifyClaims(authInfo);
//            OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(authInfo, accessToken);
//            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        } catch (InvalidTokenException e) {
//            throw new BadCredentialsException("Could not obtain user details from token", e);
//        }
        return null;
    }

    private static class NoopAuthenticationManager implements AuthenticationManager {

        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
        }

    }
}
