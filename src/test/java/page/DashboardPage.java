package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private SelenideElement dashboard = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");

    private final String cardAttribute = "data-test-id";
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        dashboard.shouldBe(visible);
        cards.get(0).shouldBe(visible);
    }

    private SelenideElement findCardElementById(String id) {
        return cards.findBy(attribute(cardAttribute, id));
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

    public TransferPage clickButtonBalanceUp(String id) {
        SelenideElement el = findCardElementById(id);
        el.lastChild().click();
        return new TransferPage();
    }
}