package com.function.authentication;

import org.junit.Test;

import java.net.MalformedURLException;

import static com.function.authentication.Authenticator.*;
import static org.junit.Assert.*;

public class AuthenticatorTest {
    private static final String MOCK_USERNAME = "abc";
    private static final String MOCK_PASSWORD = "password";
    private static final String MOCK_EMAIL = "my.@email.com";
    private Authenticator authenticator = new Authenticator();

    @Test
    public void test() throws MalformedURLException {
        authenticator.setTwoFactorPwd(DEF_TWOFACTOR_PWD);
        assertEquals(DASH_BOARD, authenticator.getUrl(MOCK_USERNAME, MOCK_PASSWORD, MOCK_EMAIL));
        assertEquals(DASH_BOARD, authenticator.getUrl(null, MOCK_PASSWORD, MOCK_EMAIL));
        assertEquals(LOG_IN, authenticator.getUrl(null, MOCK_PASSWORD, null));

        authenticator.setTwoFactorPwd(0);
        assertEquals(LOG_IN, authenticator.getUrl(MOCK_USERNAME, MOCK_PASSWORD, MOCK_EMAIL));

        authenticator.login(MOCK_USERNAME, MOCK_PASSWORD, MOCK_EMAIL);

        authenticator.setTwoFactorPwd(DEF_TWOFACTOR_PWD);
        authenticator.login(MOCK_USERNAME, MOCK_PASSWORD, MOCK_EMAIL);
        authenticator.login(null, MOCK_PASSWORD, MOCK_EMAIL);
        authenticator.login(MOCK_USERNAME, null, MOCK_EMAIL);
        authenticator.login(MOCK_USERNAME, MOCK_PASSWORD, null);
        authenticator.login(null, MOCK_PASSWORD, null);
    }

}