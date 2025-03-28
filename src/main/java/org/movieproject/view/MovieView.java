package org.movieproject.view;

import org.movieproject.model.Users;
import org.movieproject.service.UsersService;

import java.sql.Connection;
import java.util.Scanner;

public class MovieView {
    private final UsersService usersService;
    private final Scanner scanner;

    public MovieView(Connection connection) {
        this.usersService = new UsersService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("로그인 후 화면입니다. 영화 정보 띄우기");

        // 여기에 기능 메뉴 출력 + 입력 받기 등 구현
    }

    public void loginMenu(){
        System.out.print("사용자 닉네임: ");
        String nickname = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        Users loginUser = usersService.login(nickname, password);
        if (loginUser != null) {
            System.out.println("\n✅ " + loginUser.getUserNickname() + "님 환영합니다!");
            showMenu(); // ✅ 로그인 성공 시 MovieView로 이동
        }
    }


}
