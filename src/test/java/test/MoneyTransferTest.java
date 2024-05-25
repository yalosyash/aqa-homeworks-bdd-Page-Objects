package test;

import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

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

        var card1 = DataHelper.getCardInfo1();
        var card2 = DataHelper.getCardInfo2();

        int actualBalanceCard1 = dashboardPage.getCardBalance(card1);
        int actualBalanceCard2 = dashboardPage.getCardBalance(card2);

        if (actualBalanceCard1 < actualBalanceCard2) {
            var transferPage = dashboardPage.clickButtonBalanceUp(card1);
            transferPage.validTransfer(actualBalanceCard2 - 10_000, card2);
        } else if (actualBalanceCard1 > actualBalanceCard2) {
            var transferPage = dashboardPage.clickButtonBalanceUp(card2);
            transferPage.validTransfer(actualBalanceCard1 - 10_000, card1);
        }

        // Открытие страницы
        open("http://localhost:9999");
    }

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
        var cardTo = DataHelper.getCardInfo2();
        var cardFrom = DataHelper.getCardInfo1();
        int transfer = 10_000;

        // логика для проверки теста
        int card1Balance = dashboardPage.getCardBalance(cardTo) + transfer;
        int card2Balance = dashboardPage.getCardBalance(cardFrom) - transfer;

        // получаем страницу перевода
        var transferPage = dashboardPage.clickButtonBalanceUp(cardTo);

        // проводим перевод
        transferPage.validTransfer(transfer, cardFrom);

        // проверка теста
        Assertions.assertEquals(card1Balance, dashboardPage.getCardBalance(cardTo));
        Assertions.assertEquals(card2Balance, dashboardPage.getCardBalance(cardFrom));
    }

    @Test
    void shouldTransferPositiveSumFromAnotherCard() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);

        var cardTo = DataHelper.getCardInfo1();
        var cardFrom = DataHelper.getCardInfo2();
        int transfer = 10_000;

        int card1Balance = dashboardPage.getCardBalance(cardTo) + transfer;
        int card2Balance = dashboardPage.getCardBalance(cardFrom) - transfer;

        var transferPage = dashboardPage.clickButtonBalanceUp(cardTo);

        transferPage.validTransfer(transfer, cardFrom);

        Assertions.assertEquals(card1Balance, dashboardPage.getCardBalance(cardTo));
        Assertions.assertEquals(card2Balance, dashboardPage.getCardBalance(cardFrom));
    }

    @Test
    void shouldNotTransferNegativeSum() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);

        var cardTo = DataHelper.getCardInfo1();
        var cardFrom = DataHelper.getCardInfo2();

        int transfer = dashboardPage.getCardBalance(cardFrom) + 5_000;

        var transferPage = dashboardPage.clickButtonBalanceUp(cardTo);
        transferPage.validTransfer(transfer, cardFrom);

        transferPage.getError();
    }

    @Test
    void shouldClickToCancelFromTransferPage() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1 = DataHelper.getCardInfo1();
        var card2 = DataHelper.getCardInfo2();
        int expect1 = dashboardPage.getCardBalance(card1);
        int expect2 = dashboardPage.getCardBalance(card2);
        var transferPage = dashboardPage.clickButtonBalanceUp(card1);
        transferPage.clickActionCancel();
        dashboardPage.clickActionReload();
        Assertions.assertEquals(expect1, dashboardPage.getCardBalance(card1));
        Assertions.assertEquals(expect2, dashboardPage.getCardBalance(card2));
    }

    @Test
    void shouldErrorOnTransferPage() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();

        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card = DataHelper.getCardInfo1();
        var transferPage = dashboardPage.clickButtonBalanceUp(card);
        transferPage.clickSubmitButton();
        transferPage.getError();
    }
}