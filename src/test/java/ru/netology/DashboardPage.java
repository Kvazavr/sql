package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    private SelenideElement codeField = $x("//*[contains(text(), 'Личный кабинет')]");

    public DashboardPage() {
        codeField.shouldBe(Condition.visible);
    }

}
