package com.function.authentication;

import org.junit.Test;
import static org.junit.Assert.*;

public class AuthenticatorTest {
    private Authenticator authenticator = new Authenticator();

    @Test
    public void testGetUrl() {
        Authenticator.setTwoFactorPwd(12345);
        assertEquals("https://com.company.com/dashboard",
                     authenticator.getUrl("abc", "password", "my.@email.com"));
        assertEquals("https://com.company.com/dashboard",
                     authenticator.getUrl(null, "password", "my.@email.com"));
        assertEquals("http://com.company.com/login",
                     authenticator.getUrl(null, "password", null));
    }

}