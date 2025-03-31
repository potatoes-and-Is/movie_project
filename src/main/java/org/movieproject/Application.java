package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.MovieView;
import org.movieproject.view.UsersView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== POI MOIVE SYSTEM =====");
            System.out.println("1. 회원 가입");
            System.out.println("2. 로그인");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> signUpUserManagement(connection); // 회원가입
                case 2 -> logInMovieMenuManagement(connection); // 로그인
                case 0 -> {
                    connection.close();
                    System.out.println("🚀 프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("❌ 잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    // 로그인
    private static void logInMovieMenuManagement(Connection connection) {
        MovieView movieView = new MovieView(connection);
        movieView.loginUser();
    }

    // 회원가입
    private static void signUpUserManagement(Connection connection){
        MovieView movieView = new MovieView(connection);
        movieView.signUp();
    }
}
