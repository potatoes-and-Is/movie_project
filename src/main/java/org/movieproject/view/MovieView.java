package org.movieproject.view;

import org.movieproject.dao.UsersDao;
import org.movieproject.model.Users;
import org.movieproject.service.UsersService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MovieView {
    private final UsersService usersService;
    private final Scanner scanner;
    private final Connection connection;

    public MovieView(Connection connection) {
        this.usersService = new UsersService(connection);
        this.scanner = new Scanner(System.in);
        this.connection = connection;
    }

    public void showMenu() {
        System.out.println("로그인 후 화면입니다. 영화 정보 띄우기");

        // 여기에 기능 메뉴 출력 + 입력 받기 등 구현
    }

    public void loginUser(){
        System.out.print("사용자 닉네임: ");
        String nickname = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        Users loginUser = usersService.login(nickname, password);
        if (loginUser != null) {
            System.out.println("\n로그인 성공! " + loginUser.getUserNickname() + "님 환영합니다!");
            if ("root".equals(loginUser.getUserNickname())) {
                UsersView usersView = new UsersView(connection);
                usersView.showMenu();
            } else {
                showMenu(); // 일반 사용자 메뉴로 이동
            }
        }
    }



    public void signUp(){
        System.out.println("\n===== [회원가입] =====");

        System.out.print("닉네임: ");
        String nickname = scanner.nextLine();

        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        // Users 객체 생성
        Users users = new Users(0, nickname, password, "Y", LocalDateTime.now());

        try {
            boolean success = usersService.registerUser(users); // service → dao로 전달

            if (success) {
                System.out.println("회원가입이 완료되었습니다!");
            } else {
                System.out.println("회원가입에 실패했습니다.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR" + e.getMessage()); // 닉네임 중복 등
        } catch (SQLException e) {
            System.out.println("ERROR 시스템 오류 발생!");
            e.printStackTrace();
        }
    }

}
