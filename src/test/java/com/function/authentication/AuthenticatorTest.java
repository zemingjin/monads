package com.function.authentication;

import org.junit.Test;

import java.net.MalformedURLException;

public class AuthenticatorTest {
    private Authenticator authenticator = new Authenticator();

    @Test
    public void loginHappyPath() throws MalformedURLException {
        Authenticator.setTwoFactorPwd(12345);
        authenticator.login("abc", "password", "my.@email.com");
        authenticator.login(null, "password", "my.@email.com");
    }

}