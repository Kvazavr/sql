package ru.netology;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorCodeMessage = $("[data-test-id=error-notification]");
    private SelenideElement errorEmptyCodeFieldMessage = $("[data-test-id=code]");

    public VerificationPage() {

        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify() {
        codeField.setValue("1234");
        verifyButton.click();
        errorCodeMessage.shouldBe(visible).text().contains("Неверно указан код! Попробуйте ещё раз.");

    }

    public void emptyVerify() {
        codeField.setValue("");
        verifyButton.click();
        errorEmptyCodeFieldMessage.shouldBe(visible).text().contains("Поле обязательно для заполнения");
    }
}
