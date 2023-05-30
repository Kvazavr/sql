package ru.netology;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.model.AuthCode;

import java.sql.DriverManager;

public class DBUtils {
    @SneakyThrows
    public static String codeAuth() {
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

    @SneakyThrows
    public static void preperedDB() {
        var sql = "Delete from card_transactions;";
        var sql1 = "Delete from cards;";
        var sql2 = "Delete from auth_codes;";
        var sql3 = "Delete from users;";
        var sql4 = "Insert into users (id, login, password, status) values (?, ?, ?, ?);";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.execute(conn, sql);
            runner.execute(conn, sql1);
            runner.execute(conn, sql2);
            runner.execute(conn, sql3);
            runner.execute(conn, sql4, "4e2a0d13-df46-46a0-a425-bde26ba38ac1", "goga", "$2a$10$FCyeW.CTu5oYT.WPnfE2zOXOsriaY9TEre/NBQkDiOYv.lk6lA1OK", "active");
        }
    }
}
