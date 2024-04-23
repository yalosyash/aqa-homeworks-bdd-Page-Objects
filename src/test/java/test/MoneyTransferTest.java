package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class MoneyTransferTest {


    @BeforeEach
    void setup() {
//        Выключение опции проверки пароля в Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        open("http://localhost:9999");
    }

    @Test
    void should() throws InterruptedException {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        Thread.sleep(100000);
        verificationPage.validVerify(verificationCode);

    }

}