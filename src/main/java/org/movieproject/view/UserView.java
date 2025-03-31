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
        while (true) {
            System.out.println("\n===== POI MOVIE SYSTEM =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    User newUser = createUser();
                    if (newUser == null) { // createUser()가 null을 반환하면 루프 계속
                        System.out.println("회원가입에 실패했습니다. 다시 입력하여 주시기 바랍니다.");
                        continue;
                    }
                    return newUser;
                }
                case "2" -> {
                    User existingUser = getUser();
                    if (existingUser == null) { // getUser()가 null을 반환하면 루프 계속
                        System.out.println("로그인에 실패했습니다. 다시 시도하세요.");
                        continue;
                    }
                    return existingUser; // 유효한 User 객체 반환
                }
                case "0" -> {
                    System.out.println("영화 예매를 종료합니다.");
                    return null;
                }
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            }
        }
    }

    // 회원가입
    private User createUser() {
        int attempts = 0; // 시도 횟수 추적
        while (attempts < 3) {
            System.out.print("닉네임: ");
            String nickname = scanner.nextLine();

            System.out.print("비밀번호: ");
            String password = scanner.nextLine();

            User newUser = new User(nickname, password, "Y");
            if (userService.registerUser(newUser)) {
                System.out.println("회원가입 성공!");
                return newUser; // 성공 시 유저 반환
            } else {
                System.out.println("아이디가 중복되었습니다. 다시 입력하여 주시기 바랍니다.");
            }
            attempts++; // 시도 횟수 증가
        }
        System.out.println("회원가입 시도 횟수를 초과했습니다.");
        return null; // 실패 시 null 반환
    }

    // 로그인
    private User getUser() {
        int attempts = 0; // 시도 횟수 추적
        while (attempts < 3) {
            System.out.print("닉네임: ");
            String nickname = scanner.nextLine();

            System.out.print("비밀번호: ");
            String password = scanner.nextLine();

            User user = userService.login(nickname, password);
            if (user != null) {
                System.out.println("로그인 성공! 환영합니다, " + user.getNickname() + "님 !");
                return user; // 성공 시 유저 반환
            } else {
                System.out.println("아이디랑 비밀번호를 다시 입력하여 주시기 바랍니다.");
            }
            attempts++; // 시도 횟수 증가
        }
        System.out.println("로그인 시도 횟수를 초과했습니다.");
        return null; // 실패 시 null 반환
    }
}