package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.MyPageView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try (Connection connection = JDBCConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            MyPageView myPageView = new MyPageView(connection);
            myPageView.loginProcess();

        } catch (SQLException e) {
            System.err.println("⛔ 데이터베이스 연결 실패: " + e.getMessage());
        }
    }
}
