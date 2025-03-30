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

    public void showMenu(Users loginUser) {
        while (true) {
            System.out.println("/n===== 사용자 메뉴=====");
            System.out.println("1. 영화 목록 보기");
            System.out.println("0. 로그아웃");
            System.out.print("원하시는 메뉴를 선택해주세요 : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> System.out.println("현재 상영 중인 영화 목록입니다.");
                case 0 ->{
                    System.out.println("로그아웃 되었습니다. 메인 메뉴로 돌아갑니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public void loginUser() {
        System.out.print("사용자 닉네임: ");
        String nickname = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        Users loginUser = usersService.login(nickname, password);
        if (loginUser != null) {
            System.out.println("\n로그인 성공! " + loginUser.getUserNickname() + "님 환영합니다!");
            if ("root".equals(loginUser.getUserNickname())) {
                UsersView usersView = new UsersView(connection);
                usersView.showMenu(); // 관리자 메뉴 (여기에도 return 있음)
            } else {
                showMenu(loginUser); // 사용자 메뉴 → 내부에서 로그아웃하면 return
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
