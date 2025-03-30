package org.movieproject.view;

import org.movieproject.model.Tickets;
import org.movieproject.service.MyPageService;
import org.movieproject.service.TicketsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MyPageView {
    private final MyPageService myPageService;
    private final Scanner scanner;
    private Connection connection;

    private final TicketsService ticketsService;
    boolean valid = false;
    private int totalUserId;

    public MyPageView(Connection connection) {
        this.myPageService = new MyPageService(connection);
        this.ticketsService = new TicketsService(connection);
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
        totalUserId = userId;
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
                System.out.println("예매된 티켓이 없습니다.\n");
                return;
            }

            System.out.println("\n예매 내역");
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
                    System.out.print("\n취소할 티켓 ID를 입력하세요: ");
                    int ticketId = scanner.nextInt();
                    scanner.nextLine(); // 버퍼 비우기
                    ShowDetailTicket(ticketId);
                    showMenu(userId);
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
        valid = false;
            try {
                Tickets ticketInfo = myPageService.getTicketById(ticketId);

                System.out.println("\n선택한 티켓 정보");
                if (ticketInfo != null) {
                    System.out.println("티켓 ID: " + ticketInfo.getTicketId() +
                            ", 이름: " + ticketInfo.getUserNickname() +
                            ", 영화 제목: " + ticketInfo.getMovieTitle() +
                            ", 상영 시간: " + ticketInfo.getScheduleStartTime() +
                            ", 좌석 번호: " + ticketInfo.getSeatNumber());
                }

                valid = false;
                while (!valid) {
                    try {
                        System.out.print("예매를 취소하시겠습니까? (y/n): ");
                        String cancelChoice = scanner.nextLine().trim();
                        switch (cancelChoice) {
                            case "y" -> {
                                cancelTicket(ticketId);
                                System.out.println("취소 성공");
                                valid = true;
                            }
                            case "n" -> {
                                System.out.println("마이페이지로 돌아갑니다.\n");
                                return;
                            }
                            default -> {
                                System.out.println("잘못된 입력입니다. 다시 선택해주세요.\n");
//                                scanner.nextLine();
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("잘못된 입력입니다. 번호를 선택해주세요.");
                        System.out.print("선택 : ");
                        scanner.nextLine();
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("오류: " + e.getMessage());
            }
    }

//    // 1개의 취소할 예매 내역 가져오기
//    public Tickets getTicketById(List<Tickets> tickets) throws SQLException {
//        String ticketLine = "";
//        int ticketIndex = 0;
//        System.out.println("\n===== 취소할 번호 선택 =====");
//        while (true) {
//            try {
//                System.out.print("취소할 번호 : ");
//                ticketLine = scanner.nextLine();
//                ticketIndex = Integer.parseInt(ticketLine) - 1;
//                // 인덱스 오류 잡기
//                if (ticketIndex >= 0 && ticketIndex < tickets.size()) {
//                    break;
//                } else {
//                    System.out.println("취소할 예매 번호가 없습니다. 다시 입력해주세요.");
//                }
//            } catch (InputMismatchException | NumberFormatException ime) {
//                System.out.println("잘못 입력하셨습니다. 번호로 입력해주세요.");
//            }
//        }
//        return ticketsService.getTicketsById(tickets.get(ticketIndex).getTicketId());
//    }

    public void cancelTicket(int ticketId) throws SQLException {

        ticketsService.cancelTicket(ticketId);
//        // 선택한 예매 취소 여부 확인
//        System.out.println("\n===== 취소 여부 확인 =====");
//        System.out.println("취소하시겠습니까?");
//        System.out.println("1. 네.");
//        System.out.println("2. 아니오.");
//        System.out.print("선택 : ");
//
//        valid = false;
//        while (!valid) {
//            try {
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//                switch (choice) {
//                    case 1 -> {
//                        ticketsService.cancelTicket(ticketId);
//                        System.out.println("\n예매가 취소되었습니다.");
//                    }
//                    case 2 -> showMenu(totalUserId);
//                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
//                }
//                valid = true;
//            } catch (InputMismatchException e) {
//                System.out.println("잘못된 입력입니다. 번호를 선택해주세요.");
//                System.out.print("선택 : ");
//                scanner.nextLine();
//            }
//        }
    }
}

