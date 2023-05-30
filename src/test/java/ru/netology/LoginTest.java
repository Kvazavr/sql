package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    LoginPage loginPage;

    @BeforeEach
    void init() {
        DBUtils.preperedDB();
        loginPage = open("http://localhost:9999", LoginPage.class);

    }

    @Test
    void successAuthTest() {
        var verificationPage = loginPage.validLogin(DataHelper.getValidAuthInfo());
        String code = DBUtils.codeAuth();
        verificationPage.validVerify(code);
    }

    @Test
    void invalidLoginAuthTest() {
        loginPage.invalidLoginAndCheckErrorVisibleLogin(DataHelper.getInvalidLogin());
    }

    @Test
    void invalidPasswordAuthTest() {
        loginPage.invalidLoginAndCheckErrorVisibleLogin(DataHelper.getInvalidPassword());
    }

    @Test
    void invalidCodeTest() {
        var verificationPage = loginPage.validLogin(DataHelper.getValidAuthInfo());
        verificationPage.invalidVerify();
    }

    @Test
    void emptyFieldCodeTest() {
        var verificationPage = loginPage.validLogin(DataHelper.getValidAuthInfo());
        verificationPage.emptyVerify();
    }

}
