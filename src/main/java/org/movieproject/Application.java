package org.movieproject;

import org.movieproject.config.JDBCConnection;
<<<<<<< HEAD
<<<<<<< HEAD
import org.movieproject.view.MovieView;
import org.movieproject.view.UsersView;
=======
import org.movieproject.view.MyPageView;
>>>>>>> f4f71e99c44b1d1eca80a9f22f32ec61ab10210d
=======
import org.movieproject.view.MyPageView;
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
<<<<<<< HEAD
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

<<<<<<< HEAD
        while (true) {
            System.out.println("\n===== POI MOIVE SYSTEM =====");
            System.out.println("1. 회원 가입");
            System.out.println("2. 로그인");
            System.out.println("0. 종료");
            System.out.print("선택: ");

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
        }
    }

    /**
     * 📌 사용자(User) 관리 시작
     * - 사용자(User) 관련 기능 실행
     */
    private static void startUserManagement(Connection connection) {
        UsersView userView = new UsersView(connection);
        userView.showMenu();
    }

    private static void logInMovieMenuManagement(Connection connection) {
        MovieView movieView = new MovieView(connection);
        movieView.loginUser();
    }

    private static void signUpUserManagement(Connection connection){
        MovieView movieView = new MovieView(connection);
        movieView.signUp();
=======
        System.out.println("로그인 성공");
        System.out.println("1. 예매 내역 확인");
        System.out.print("선택 : ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> startTicketsManager(connection);
            case 0 -> {
                connection.close();
                System.out.println("프로그램을 종료합니다.");
            }
            default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    public static void startTicketsManager(Connection connection) throws SQLException {
        MyPageView myPageView = new MyPageView(connection);
        int userId = 1;
        myPageView.loginProcess();
>>>>>>> f4f71e99c44b1d1eca80a9f22f32ec61ab10210d
=======
    public static void main(String[] args) {
        try (Connection connection = JDBCConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            MyPageView myPageView = new MyPageView(connection);
            myPageView.loginProcess();

        } catch (SQLException e) {
            System.err.println("⛔ 데이터베이스 연결 실패: " + e.getMessage());
        }
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    }
}
