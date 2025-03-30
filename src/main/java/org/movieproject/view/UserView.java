package org.movieproject.view;

import org.movieproject.model.User;
import org.movieproject.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserView {
    private final UserService userService;
    private final Scanner scanner;

    public UserView(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }


    // 회원가입과 로그인을 선택하는 메뉴
    public User displayUserMenu() throws SQLException {
        System.out.println("\n===== POI MOVIE SYSTEM =====");
        System.out.println("1. 회원 가입");
        System.out.println("2. 로그인");
        System.out.println("0. 종료");
        System.out.print("선택: ");

        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            System.out.print("닉네임: ");
            String nickname = scanner.nextLine();

            System.out.print("비밀번호: ");
            String password = scanner.nextLine();

            User newUser = new User(nickname, password, "Y");
            if (userService.registerUser(newUser)) {
                System.out.println("회원가입 성공!");
                return newUser;
            } else {
                System.out.println("회원가입 실패!");
                return null;
            }
        } else if ("2".equals(choice)) {
            System.out.print("닉네임: ");
            String nickname = scanner.nextLine();

            System.out.print("비밀번호: ");
            String password = scanner.nextLine();

            User user = userService.login(nickname, password);
            if (user != null) {
                System.out.println("로그인 성공! 환영합니다, " + user.getNickname());
                return user;
            } else {
                System.out.println("로그인 실패!");
                return null;
            }
        } else {
            System.out.println("잘못된 입력입니다.");
            return null;
        }
    }
}
