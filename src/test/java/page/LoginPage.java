package page;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private void fillingForm(String login, String password) {
        $(inputName).setValue(login);
        $(inputPassword).setValue(password);
        $(buttonSubmit).click();
    }
}
