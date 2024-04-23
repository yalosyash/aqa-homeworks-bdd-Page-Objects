package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class AuthTest {
    String inputName = "[data-test-id=login] [class=input__control]";
    String inputPassword = "[data-test-id=password] [class=input__control]";
    String buttonSubmit = "[data-test-id=action-login]";
    String privateCabinet = ".heading";
    String error = "[data-test-id=error-notification]";

    private void fillingForm(String login, String password) {
        $(inputName).setValue(login);
        $(inputPassword).setValue(password);
        $(buttonSubmit).click();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void should() {

    }

}