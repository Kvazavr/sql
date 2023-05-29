package ru.netology;

import lombok.Value;

public class DataHelper {
    private DataHelper() {

    }
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getValidAuthInfo() {

        return new AuthInfo("goga", "qwerty123");
    }


}
