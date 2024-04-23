package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement inputName = $("[data-test-id=login] input");
    private SelenideElement inputPassword = $("[data-test-id=password] input");
    private SelenideElement submitButton = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        inputName.shouldBe(visible);
        inputName.setValue(info.getLogin());
        inputPassword.setValue(info.getPassword());
        submitButton.click();
        return new VerificationPage();
    }
}