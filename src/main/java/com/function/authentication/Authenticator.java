package com.function.authentication;

import com.function.model.User;

import java.net.MalformedURLException;
import java.net.URL;

class Authenticator {
    private static final String DASH_BOARD = "https://com.company.com/dashboard";
    private static final String LOG_IN = "http://com.company.com/login";
    private static long TWO_FACTOR_PWD = 123456;

    private User authenticateWithUsername(String userName, String password) throws Exception {
        System.out.println("authenticateWithUsername: userName=" + userName);
        if (userName != null && !userName.trim().isEmpty()) {
            return new User(userName, password, "");
        }
        throw new Exception("Authentication with Username/Password failed");
    }

    private User authenticateWithEmail(String email, String password) throws Exception {
        System.out.println("authenticateWithEmail: email=" + email);
        if (email != null && !email.trim().isEmpty()) {
            return new User("", password, email);
        }
        throw new Exception("Authentication with Username/Password failed");
    }

    private String twoFactor(User user, long twofPassword) throws Exception {
        System.out.println("Inside twoFactor");
        if (user == null || twofPassword == 0) {
            throw new Exception("Invalid credentials for 2 way auth");
        }
        return DASH_BOARD;
    }

    void login(String userName, String password, String email) throws MalformedURLException {
        User user = null;
        String target = LOG_IN;

        try {
            user = authenticateWithUsername(userName, password);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            try {
                user = authenticateWithEmail(email, password);
            } catch (Exception ex2) {
                System.out.println("Exception: " + ex2.getMessage());
            }
        }
        if (user != null) {
            try {
                target = twoFactor(user, TWO_FACTOR_PWD);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
        redirect(new URL(target));
    }

    private void redirect(URL url) {
        System.out.println("Redirecting to " + url);
    }

    static void setTwoFactorPwd(long twoFactorPwd) {
        TWO_FACTOR_PWD = twoFactorPwd;
    }
}
