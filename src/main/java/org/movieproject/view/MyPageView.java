package org.movieproject.view;

import org.movieproject.model.Tickets;
import org.movieproject.service.MyPageService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MyPageView {
    private final MyPageService myPageService;
    private final Scanner scanner;
    private Connection connection;

    public MyPageView(Connection connection) {
        this.myPageService = new MyPageService(connection);
        this.scanner = new Scanner(System.in);
    }

    /**
     * 📌 생성자 (Connection 주입)
     * - 데이터베이스 연결을 `Application`에서 주입받아 사용
     *
     */

    public void loginProcess() {
        System.out.print("닉네임을 입력하세요: ");
        String nickname = scanner.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        int userId = myPageService.validateLogin(nickname, password);

        if (userId != -1) {
            System.out.println("✅ 로그인 성공! 회원 ID: " + userId);
            showMenu(userId);
        } else {
            System.out.println("❌ 로그인 실패. 다시 시도해주세요.");
        }
    }

    /**
     * 📌 역할 조회 메뉴 시작
     * - 사용자 입력을 받아 역할 정보를 조회
     */
    public void showMenu(int userId) {
        while (true) {
            System.out.println("1. 예매정보 확인하기"); // 1번 클릭 후 회원의 티켓id 목록+ 영화제목, 상영시간만 나오도록
            System.out.println("2. 영화 예매하기");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1 -> ticketInfoById();
                case 2 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }
    }


    /**
     * 순서 . 로그인 -> 1. 예매정보 확인하기 클릭하면 -> 회원들의 예매정보에 대한 티켓정보리스트 간단히 보여줌 ->
     *
     * y면 삭제 n이면 예매정보 확인하기 창으로
     */
    // 티켓 아이디(1번) 입력하면 한 건에 대한 전체 정보창 보여줌 이 창에서 여기서 취소하시겠습니까 정하기
    private void ticketInfoById() {
        System.out.print("조회할 티켓 ID를 입력하세요: ");
        int ticketId = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        try {
            Tickets ticketInfo = myPageService.getTicketInfoById(ticketId);
            System.out.println("🎟️ 예매 정보: " + ticketInfo);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 오류: " + e.getMessage());
        }
    }
}
