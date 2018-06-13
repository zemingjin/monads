package com.function.authentication;

import com.function.model.User;
import com.function.monad.Try;

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
        redirect(new URL(getUrl(userName, password, email)));
    }

    String getUrl(String userName, String password, String email) {
        return Try.with(() -> authenticateWithUsername(userName, password))
                  .recover(e -> Try.with(() -> authenticateWithEmail(email, password)))
                  .flatMap(user -> Try.with(() -> twoFactor(user, TWO_FACTOR_PWD)))
                  .orElse(LOG_IN);
    }

    private void redirect(URL url) {
        System.out.println("Redirecting to " + url);
    }

    static void setTwoFactorPwd(long twoFactorPwd) {
        TWO_FACTOR_PWD = twoFactorPwd;
    }
}
