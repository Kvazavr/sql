package ru.netology;

import com.codeborne.selenide.Condition;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.model.AuthCode;
import ru.netology.model.User;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.*;

public class LoginTest {
    @SneakyThrows
    @BeforeEach
    void init(){
        var sql = "Delete FROM auth_codes;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.execute(conn, sql);
        }

    }
    @Test
    void successAuthTest() {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue("vasya");
        $("[data-test-id=password] input").setValue("qwerty123");
        $("[data-test-id=action-login]").click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        $("[data-test-id=code] input").setValue(codeAuth());
        $("[data-test-id=action-verify]").click();
        $x("//*[contains(text(), 'Личный кабинет')]").shouldBe(Condition.visible);
    }

    @SneakyThrows
    private String codeAuth() {
        var sql = "SELECT * FROM auth_codes order by created desc limit 1;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var code = runner.query(conn, sql, new BeanHandler<>(AuthCode.class));
            System.out.println(code);
            return code.getCode();
        }
    }
    @Test
    void invalidPasswordAuthTest() {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue("vasya");
        $("[data-test-id=password] input").setValue("123");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").text().contains("Неверно указан логин или пароль");
    }
    @Test
    void invalidLoginAuthTest() {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue("petya");
        $("[data-test-id=password] input").setValue("qwerty123");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").text().contains("Неверно указан логин или пароль");
    }
    @Test
    void invalidCodeTest() {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue("vasya");
        $("[data-test-id=password] input").setValue("qwerty123");
        $("[data-test-id=action-login]").click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        $("[data-test-id=code] input").setValue("123456");
        $("[data-test-id=action-verify]").click();
        $("[data-test-id=error-notification]").text().contains("Неверно указан код! Попробуйте ещё раз.");

    }
@Test
void emptyFieldCodeTest() {
    open("http://localhost:9999");
    $("[data-test-id=login] input").setValue("vasya");
    $("[data-test-id=password] input").setValue("qwerty123");
    $("[data-test-id=action-login]").click();
    $("[data-test-id=code] input").setValue("");
    $("[data-test-id=action-verify]").click();
    $("[data-test-id=code]").text().contains("Поле обязательно для заполнения");

}

}
