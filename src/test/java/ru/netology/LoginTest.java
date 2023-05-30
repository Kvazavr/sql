package ru.netology;

import com.codeborne.selenide.Condition;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.model.AuthCode;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.*;

public class LoginTest {
    LoginPage loginPage;

    @BeforeEach
    void init() {
        DBUtils.preperedDB();
        open("http://localhost:9999");

    }

    @Test
    void successAuthTest() {
        var verificationPage = new LoginPage().validLogin(DataHelper.getValidAuthInfo());
        String code = DBUtils.codeAuth();
        verificationPage.validVerify(code);
    }

    @Test
    void invalidPasswordAuthTest() {
        var Page = new LoginPage().validLogin(DataHelper.getInvalidPassword());
        loginPage.checkErrorVisibleLogin();
    }

    @Test
    void invalidLoginAuthTest() {
        var Page = new LoginPage().validLogin(DataHelper.getInvalidLogin());
        loginPage.checkErrorVisibleLogin();
    }

    @Test
    void invalidCodeTest() {
        var verificationPage = new LoginPage().validLogin(DataHelper.getValidAuthInfo());
        verificationPage.invalidVerify();
    }

    @Test
    void emptyFieldCodeTest() {
        var verificationPage = new LoginPage().validLogin(DataHelper.getValidAuthInfo());
        verificationPage.emptyVerify();
    }

}
