package com.function.authentication;

import com.function.model.User;
import com.function.monad.Try;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

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

    private static final Consumer<Exception> handler = e -> System.out.println("Exception: " + e.getMessage());

    String getUrl(String userName, String password, String email) {
        return Try.with(() -> authenticateWithUsername(userName, password), handler)
                  .recoverWith(e -> Try.with(() -> authenticateWithEmail(email, password), handler))
                  .then(user -> Try.with(() -> twoFactor(user, twoFactorPwd), handler))
                  .orElse(LOG_IN);
    }

    private void redirect(URL url) {
        System.out.println("Redirecting to " + url);
    }

    void setTwoFactorPwd(long value) {
        this.twoFactorPwd = value;
    }
}
