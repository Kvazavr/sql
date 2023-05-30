package ru.netology;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement errorLoginMessage =  $("[data-test-id=error-notification]");
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new VerificationPage();
    }
    public void checkErrorVisibleLogin() {
        errorLoginMessage.shouldBe(visible);
        errorLoginMessage.text().contains("Неверно указан логин или пароль");
    }
}
