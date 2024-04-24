package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
    void setup() {
//      Выключение опции проверки пароля в Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        open("http://localhost:9999");
    }

    String cardId1 = "92df3f1c-a033-48e6-8390-206f6b1f56c0";
    String cardId2 = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";

    String cardFrom1 = "5559 0000 0000 0001";
    String cardFrom2 = "5559 0000 0000 0002";

    @Test
    void should() throws InterruptedException {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);

//        dashboardPage.getCardBalance(cardId1);
        dashboardPage.validTransfer(5000, cardFrom2, cardId1);

//        Thread.sleep(1000);

//        int balance = dashboardPage.getCardBalance(card0001);
//        Assertions.assertEquals(balance, 10_000);

    }

}