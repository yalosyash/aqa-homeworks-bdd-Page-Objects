package data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

@Value
    public static class AuthInfo {
        String login;
        String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }
}

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;

        public VerificationCode(String code) {
            this.code = code;
        }
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }
}