package ru.netology;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorCodeMessage =  $("[data-test-id=error-notification]");
    private SelenideElement emptyCodeFieldMessage = $("[data-test-id=code]");

    public VerificationPage() {

        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public DashboardPage invalidVerify() {
        codeField.setValue("1234");
        verifyButton.click();
        return checkErrorVisible();
    }
    public DashboardPage emptyVerify() {
        codeField.setValue("1234");
        verifyButton.click();
        return checkErrorVisibleEmptyField();
    }
    public DashboardPage checkErrorVisible() {
        errorCodeMessage.shouldBe(visible);
        errorCodeMessage.text().contains("Неверно указан код! Попробуйте ещё раз.");
        return null;
    }
    public DashboardPage checkErrorVisibleEmptyField() {
        emptyCodeFieldMessage.shouldBe(visible);
        emptyCodeFieldMessage.text().contains("Поле обязательно для заполнения");
        return null;
    }
}
