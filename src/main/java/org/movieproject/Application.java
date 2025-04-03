package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.MovieView;
import org.movieproject.view.UsersView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.SeatsView;

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

            try {   // 예외처리 추가 -> 숫자만
                int choice = scanner.nextInt();
                scanner.nextLine(); // 개행 문자 처리

                switch (choice) {
                    case 1 -> signUpUserManagement(connection);
                    case 2 -> logInMovieMenuManagement(connection);
                    case 0 -> {
                        connection.close();
                        System.out.println("🚀 프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("❌ 잘못된 입력입니다. 다시 선택하세요.");
                }
            } catch (InputMismatchException e){
                System.out.println("메뉴에 있는 숫자(정수)만 입력해주세요");
                scanner.nextLine();
            }
        }
    }

    /**
     * 📌 사용자(User) 관리 시작
     * - 사용자(User) 관련 기능 실행
     */
    private static void logInMovieMenuManagement(Connection connection) {
        MovieView movieView = new MovieView(connection);
        movieView.loginUser();
    }

    private static void signUpUserManagement(Connection connection){
        MovieView movieView = new MovieView(connection);
        movieView.signUp();
    }
}
