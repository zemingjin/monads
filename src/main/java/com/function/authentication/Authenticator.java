package com.function.authentication;

import com.function.model.User;

import java.net.MalformedURLException;
import java.net.URL;

class Authenticator {
    static final String DASH_BOARD = "https://com.company.com/dashboard";
    static final String LOG_IN = "http://com.company.com/login";
    static final long DEF_TWOFACTOR_PWD = 123456;

    private long twoFactorPwd = DEF_TWOFACTOR_PWD;

    private User authenticateWithUsername(String userName, String password) throws Exception {
        if (userName != null && !userName.trim().isEmpty()) {
            return new User(userName, password, "");
        }
        throw new Exception("authenticateWithUsername failure");
    }

    private User authenticateWithEmail(String email, String password) throws Exception {
        if (email != null && !email.trim().isEmpty()) {
            return new User("", password, email);
        }
        throw new Exception("authenticateWithEmail failure");
    }

    private String twoFactor(User user, long twofPassword) throws Exception {
        if (user == null || twofPassword == 0) {
            throw new Exception("twoFactor authentication failure ");
        }
        return DASH_BOARD;
    }

    void login(String userName, String password, String email) throws MalformedURLException {
        redirect(new URL(getUrl(userName, password, email)));
    }

    String getUrl(String userName, String password, String email) {
        User user = null;
        String target;

        try {
            user = authenticateWithUsername(userName, password);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            try {
                user = authenticateWithEmail(email, password);
            } catch (Exception e2) {
                System.out.println("Exception: " + e2.getMessage());
            }
        }
        if (user != null) {
            try {
                target = twoFactor(user, twoFactorPwd);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                target = LOG_IN;
            }
        } else {
            target = LOG_IN;
        }
        return target;
    }

    private void redirect(URL url) {
        System.out.println("Redirecting to " + url);
    }

    void setTwoFactorPwd(long value) {
        this.twoFactorPwd = value;
    }
}
