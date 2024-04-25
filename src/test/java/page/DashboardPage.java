package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private SelenideElement dashboard = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");

    private SelenideElement inputAmount = $("[data-test-id=amount] input");
    private SelenideElement inputFrom = $("[data-test-id=from] input");
    private SelenideElement submitButton = $("[data-test-id=action-transfer]");

    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    private String cardAttribute = "data-test-id";

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        dashboard.shouldBe(visible);
        cards.get(0).shouldBe(visible);
    }

    private SelenideElement findCardElementById(String id) {
        for (SelenideElement el : cards) {
            String cardId = el.getAttribute(cardAttribute);
            if (cardId.equals(id)) {
                return el;
            }
        }
        return null;
    }

    public int getCardBalance(String id) {
        String text = findCardElementById(id).shouldBe(visible).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish);
        return (int) Math.round(Double.parseDouble(value.replace(",", "."))); // отбрасываю копейки, меняю запятые на точки
    }

    public void clickButtonBalanceUp(String id) {
        SelenideElement el = findCardElementById(id);
        el.lastChild().click();
    }

    public void validTransfer(int amount, String numberFrom, String IdTo) {
        clickButtonBalanceUp(IdTo);

        $("h1").shouldBe(visible).shouldHave(text("Пополнение карты"));
        inputAmount.shouldBe(visible).setValue(Integer.toString(amount));
        inputFrom.setValue(numberFrom);
        submitButton.click();
    }

    public void getError() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка"));
    }
}