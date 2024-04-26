package test;

import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import java.util.Random;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
    void setup() {

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
            var transferPage = dashboardPage.clickButtonBalanceUp(cardId1);
            transferPage.validTransfer(actualBalanceCard2 - 10_000, fromCard2);
        } else if (actualBalanceCard1 > actualBalanceCard2) {
            var transferPage = dashboardPage.clickButtonBalanceUp(cardId2);
            transferPage.validTransfer(actualBalanceCard1 - 10_000, fromCard1);
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

        // получаем страницу перевода
        var transferPage = dashboardPage.clickButtonBalanceUp(cardId2);

        // проводим перевод
        transferPage.validTransfer(transfer, from);

        // проверка теста
        Assertions.assertEquals(card1Balance, dashboardPage.getCardBalance(cardId1));
        Assertions.assertEquals(card2Balance, dashboardPage.getCardBalance(cardId2));
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

        var transferPage = dashboardPage.clickButtonBalanceUp(cardId1);
        transferPage.validTransfer(transfer, from);

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

        int transfer = dashboardPage.getCardBalance(cardId1) + 5_000;

        var transferPage = dashboardPage.clickButtonBalanceUp(cardId2);
        transferPage.validTransfer(transfer, from);

        transferPage.getError();
    }

    @Test
    void shouldClickToCancelFromTransferPage() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);
        int expect = dashboardPage.getCardBalance(cardId2);
        var transferPage = dashboardPage.clickButtonBalanceUp(cardId2);
        transferPage.clickActionCancel();
        dashboardPage.clickActionReload();
        Assertions.assertEquals(expect, dashboardPage.getCardBalance(cardId2));
    }
}