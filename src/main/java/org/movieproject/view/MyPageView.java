package org.movieproject.view;

import org.movieproject.model.Tickets;
import org.movieproject.service.MyPageService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MyPageView {
    private final MyPageService myPageService;
    private final Scanner scanner;
    private Connection connection;

    public MyPageView(Connection connection) {
        this.myPageService = new MyPageService(connection);
        this.scanner = new Scanner(System.in);
    }

    /* 테스트용 로그인 */
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

     /* 로그인 성공후 예매정보 확인하기와 영화 예매하기를 선택할 수 있음. */
    public void showMenu(int userId) {
        while (true) {
            System.out.println("1. 예매정보 확인하기");
            System.out.println("2. 영화 예매하기");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1 -> showUserTickets(userId);
                case 2 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }
    }

    /* 예매 정보 확인하기 입력 시 회원의 예매 목록 출력 */
    private void showUserTickets(int userId) {
        try {
            List<Tickets> tickets = myPageService.getTicketsByUserId(userId);

            if (tickets.isEmpty()) {
                System.out.println("예매된 티켓이 없습니다.");
                return;
            }

            System.out.println("예매 내역");
            for (Tickets ticket : tickets) {
                System.out.println("티켓 ID: " + ticket.getTicketId() +
                        ", 영화 제목: " + ticket.getMovieTitle() +
                        ", 상영 시간: " + ticket.getScheduleStartTime());
                //      ", 영화 제목: " + ticket.cinemaId.scheduleId.movieId.getmovieTitle() +
                //      ", 상영 시간: " + ticket.cinemaId.scheduleId.getScheduleTime());
            }
            // 예매취소하기, 뒤로가기
            System.out.println("1. 예매 취소하기");
            System.out.println("2. 뒤로 가기");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1 -> {
                    System.out.print("취소할 티켓 ID를 입력하세요: ");
                    int ticketId = scanner.nextInt();
                    scanner.nextLine(); // 버퍼 비우기
                    ShowDetailTicket(ticketId);
                }
                case 2 -> {
                    System.out.println("뒤로 가기.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("예매 정보 조회 중 오류 발생: " + e.getMessage());
        }
    }

    /* 입력된 티켓 아이디에 대한 상세 정보 출력 */
    private void ShowDetailTicket(int ticketId) throws SQLException {
        try {
            Tickets ticketInfo = myPageService.getTicketById(ticketId);
//            System.out.print("조회할 티켓 ID를 입력하세요: ");
//            int ticketId = scanner.nextInt();
//            scanner.nextLine(); // 버퍼 비우기
//
//            ShowDetailTicket(ticketId); // 티켓 상세 조회
            System.out.println("\n선택한 티켓 정보");
            if (ticketInfo != null) {
                System.out.println("티켓 ID: " + ticketInfo.getTicketId() +
                        ", 이름: " + ticketInfo.getUserNickname() +
                        ", 영화 제목: " + ticketInfo.getMovieTitle() +
                        ", 상영 시간: " + ticketInfo.getScheduleStartTime() +
                        ", 좌석 번호: " + ticketInfo.getSeatNumber());
            }
            System.out.print("예매를 취소하시겠습니까? (y/n): ");
            String cancelChoice = scanner.nextLine().trim();

            if (cancelChoice.equalsIgnoreCase("y")) {
                System.out.println("취소 성공");
            } else {
                System.out.println("잘못된 입력입니다.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("오류: " + e.getMessage());
        }
    }
}

