package org.movieproject.view;

import org.movieproject.model.Users;
import org.movieproject.service.UsersService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

public class UsersView {
    private final UsersService usersService;
    private final Scanner scanner;

    /**
     * 📌 생성자 (UserView)
     * - `UserService` 객체를 생성하여 주입받고, 사용자 입력을 받기 위한 `Scanner` 객체 초기화
     *
     * @param connection 데이터베이스 연결을 위한 `Connection` 객체
     */
    public UsersView(Connection connection) {
        this.usersService = new UsersService(connection);
        this.scanner = new Scanner(System.in);
    }

    /**
     * 📌 사용자 메뉴 출력
     * - 사용자가 CRUD 기능을 선택할 수 있도록 메뉴를 제공
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n===== 사용자 관리 시스템 =====");
            System.out.println("1. 전체 사용자 조회");
            System.out.println("2. 사용자 등록");
            System.out.println("3. 사용자 단일 조회 (ID)");
            System.out.println("0. 종료");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> getAllUsers();
                case 2 -> registerUser();
                case 3 -> getUserById();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    /**
     * 📌 전체 사용자 조회
     * - `UserService`의 `getAllUsers()` 메서드를 호출하여 사용자 목록을 출력
     */
    private void getAllUsers() {
        try {
            List<Users> users = usersService.getAllUsers();

            if (users.isEmpty()) {
                System.out.println("등록된 사용자가 없습니다.");
            } else {
                System.out.println("\n===== 전체 사용자 목록 =====");
                users.forEach(user -> System.out.println(user));
            }
        } catch (SQLException e) {
            System.out.println("사용자 목록을 조회하는 중 오류가 발생했습니다.");
        }
    }

    /**
     * 📌 단일 사용자 조회
     * - 사용자 ID를 입력받아 해당 사용자의 정보를 출력
     */
    private void getUserById() {
        System.out.print("조회할 사용자 ID를 입력하세요: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리

        try {
            Users users = usersService.getUserById(userId);
            System.out.println("\n===== 사용자 정보 =====");
            System.out.println(users);
        } catch (SQLException e) {
            System.out.println("사용자 조회 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 📌 사용자 등록 (CREATE)
     * - 사용자 정보를 입력받아 새로운 사용자를 등록
     */
    private void registerUser() {
        System.out.print("사용자 닉네임: ");
        String nickname = scanner.nextLine();

        System.out.print("비밀번호: ");
        String password = scanner.nextLine();
        Users users = new Users(0, nickname, password, "Y", LocalDateTime.now());
        try {
            boolean success = usersService.registerUser(users);
            if (success) {
                System.out.println("사용자가 성공적으로 등록되었습니다.");
            } else {
                System.out.println("사용자 등록에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.out.println("사용자 등록 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }




}
