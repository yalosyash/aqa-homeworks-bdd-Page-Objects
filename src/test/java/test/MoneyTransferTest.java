package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import org.junit.jupiter.api.Assertions;
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

        // Выключение опции проверки пароля в Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        // Сброс балансов до начальных значений
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);

        int actualBalanceCard1 = dashboardPage.getCardBalance(cardId1);
        int actualBalanceCard2 = dashboardPage.getCardBalance(cardId2);

        if (actualBalanceCard1 < actualBalanceCard2) {
            dashboardPage.validTransfer(actualBalanceCard2 - 10_000, fromCard2, cardId1);
        } else if (actualBalanceCard1 > actualBalanceCard2) {
            dashboardPage.validTransfer(actualBalanceCard1 - 10_000, fromCard1, cardId2);
        }

        // Открытие страницы
        open("http://localhost:9999");
    }

    String cardId1 = "92df3f1c-a033-48e6-8390-206f6b1f56c0";
    String cardId2 = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";

    String fromCard1 = "5559 0000 0000 0001";
    String fromCard2 = "5559 0000 0000 0002";

    @Test
    void shouldTransferPositiveSum() {

        // авторизация
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        // получаем страницу с картами
        var dashboardPage = verificationPage.validVerify(verificationCode);

        // откуда и куда хотим перевести
        String from = fromCard1;
        String to = cardId2;
        int transfer = 10_000;

        // логика для проверки теста
        int card1Balance = dashboardPage.getCardBalance(cardId1) - transfer;
        int card2Balance = dashboardPage.getCardBalance(cardId2) + transfer;

        // проводим перевод
        dashboardPage.validTransfer(transfer, from, to);

        // проверка теста
        Assertions.assertEquals(card1Balance, dashboardPage.getCardBalance(cardId1));
        Assertions.assertEquals(card2Balance, dashboardPage.getCardBalance(to));
    }

    @Test
    void shouldTransferPositiveSumFromAnotherCard() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);

        String from = fromCard2;
        String to = cardId1;
        int transfer = 10_000;

        int card1Balance = dashboardPage.getCardBalance(cardId1) + transfer;
        int card2Balance = dashboardPage.getCardBalance(cardId2) - transfer;

        dashboardPage.validTransfer(transfer, from, to);

        Assertions.assertEquals(card1Balance, dashboardPage.getCardBalance(cardId1));
        Assertions.assertEquals(card2Balance, dashboardPage.getCardBalance(cardId2));
    }

    @Test
    void shouldNotTransferNegativeSum() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);

        String from = fromCard1;
        String to = cardId2;
        int transfer = dashboardPage.getCardBalance(cardId1) + 5_000;

        dashboardPage.validTransfer(transfer, from, to);

        dashboardPage.getError();
    }
}