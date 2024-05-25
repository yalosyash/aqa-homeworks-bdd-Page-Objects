package data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;

//        public AuthInfo(String login, String password) {
//            this.login = login;
//            this.password = password;
//        }
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;

//        public VerificationCode(String code) {
//            this.code = code;
//        }
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        String id;
        String number;

//        public Card(String id, String number) {
//            this.id = id;
//            this.number = number;
//        }
    }

    public static Card getCardInfo1() {
        return new Card("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static Card getCardInfo2() {
        return new Card("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }
}