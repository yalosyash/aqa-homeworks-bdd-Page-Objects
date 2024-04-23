package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement dashboard = $("[data-test-id=dashboard]");

    public DashboardPage() {
        dashboard.shouldBe(visible);
    }

//    public DashboardPage validVerify (DataHelper.VerificationCode verificationCode) {
//        inputCode.setValue(verificationCode.getCode());
//        submitButton.click();
//        return new DashboardPage();
//    }
}
