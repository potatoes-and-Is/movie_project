//package org.movieproject.view;
//
//import org.movieproject.model.Tickets;
//import org.movieproject.service.MyPageService;
//import org.movieproject.service.TicketsService;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Scanner;
//
//public class MyPageView {
//    private final MyPageService myPageService;
//    private final Scanner scanner;
//    private Connection connection;
//
//    private final TicketsService ticketsService;
//    boolean valid = false;
//    private int totalUserId;
//
//    public MyPageView(Connection connection) {
//        this.myPageService = new MyPageService(connection);
//        this.ticketsService = new TicketsService(connection);
//        this.scanner = new Scanner(System.in);
//    }
//
//    /* 예매정보 확인하기와 영화 예매하기를 선택할 수 있음. */
//    public void showMenu(int userId) {
//        totalUserId = userId;
//        while (true) {
//            try {
//                System.out.println("1. 예매정보 확인하기");
//                System.out.println("2. 영화 예매하기");
//                System.out.print("선택: ");
//
//                int choice = scanner.nextInt();
//                scanner.nextLine(); // 버퍼 비우기
//
//                switch (choice) {
//                    case 1 -> {
//                        showUserTickets(userId);
//                        return;
//                    }
//                    case 2 -> {
//                        return;
//                    }
//                    default -> System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("잘못된 입력입니다. 번호를 입력해주세요.");
//                scanner.nextLine(); // 입력 버퍼 비우기
//            }
//        }
//    }
//
//    /* 예매 정보 확인하기 입력 시 회원의 예매 목록 출력 */
//    private void showUserTickets(int userId) {
//        try {
//            List<Tickets> tickets = myPageService.getTicketsByUserId(userId);
//
//            if (tickets.isEmpty()) {
//                System.out.println("예매된 티켓이 없습니다.\n");
//                return;
//            }
//
//            System.out.println("\n예매 내역");
//            for (Tickets ticket : tickets) {
//                System.out.println("티켓 ID: " + ticket.getTicketId() +
//                        ", 영화 제목: " + ticket.getMovieTitle() +
//                        ", 상영 시간: " + ticket.getScheduleStartTime());
//                //      ", 영화 제목: " + ticket.cinemaId.scheduleId.movieId.getmovieTitle() +
//                //      ", 상영 시간: " + ticket.cinemaId.scheduleId.getScheduleTime());
//            }
//            checkCancelTicket(tickets);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /* 예매 취소 선택 후 취소할 티켓 ID 입력*/
//    private void checkCancelTicket(List<Tickets> tickets) throws SQLException {
//        while (true) {
//            System.out.println("1. 예매 취소하기");
//            System.out.println("2. 뒤로 가기");
//            System.out.print("선택: ");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1" -> {
//                    System.out.print("\n취소할 티켓 ID를 입력하세요: ");
//                    String showDetailTicketId = scanner.nextLine();
//                    for (Tickets ticket : tickets) {
//                        if (String.valueOf(ticket.getTicketId()).equals(showDetailTicketId)) {
//                            showDetailTicket(Integer.parseInt(showDetailTicketId));
//                            return;
//                        }
//                    }
//                    System.out.println("티켓 ID를 다시 입력하세요.");
//                }
//                case "2" -> {
//                    System.out.println("뒤로 가기.");
//                    return;
//                }
//                default -> System.out.println("다시 입력하세요.");
//            }
//        }
//    }
//
//    /* 선택한 티켓의 상세정보 출력 */
//    public void showDetailTicket(int ticketId) throws SQLException {
//        Tickets ticketInfo = myPageService.getTicketById(ticketId);
//        System.out.println("\n선택한 티켓 정보");
//        if (ticketInfo != null) {
//            System.out.println("티켓 ID: " + ticketInfo.getTicketId() +
//                    ", 이름: " + ticketInfo.getUserNickname() +
//                    ", 영화 제목: " + ticketInfo.getMovieTitle() +
//                    ", 상영 시간: " + ticketInfo.getScheduleStartTime() +
//                    ", 좌석 번호: " + ticketInfo.getSeatNumber());
//        }
//        cancelTicket(ticketId);
//    }
//
//    /* 예매 취소 진행 */
//    public void cancelTicket(int ticketId) throws SQLException {
//        while (true) {
//            try {
//                System.out.print("예매를 취소하시겠습니까? (Y/N): ");
//                String cancelChoice = scanner.nextLine().trim().toUpperCase();
//                switch (cancelChoice) {
//                    case "Y" -> {
//                        ticketsService.cancelTicket(ticketId);
//                        System.out.println("취소 성공");
//                        return;
//                    }
//                    case "N" -> {
//                        System.out.println("마이페이지로 돌아갑니다.\n");
//                        return;
//                    }
//                    default -> {
//                        System.out.println("잘못된 입력입니다. 다시 선택해주세요.\n");
//                        scanner.nextLine();
//                    }
//                }
//            } catch (IllegalArgumentException e) {
//                System.out.println("오류: " + e.getMessage());
//            }
//        }
//    }
//};
//
//
////    public void cancelTicket(int ticketId) throws SQLException {
////        ticketsService.cancelTicket(ticketId);
//////         선택한 예매 취소 여부 확인
////        System.out.println("\n===== 취소 여부 확인 =====");
////        System.out.println("취소하시겠습니까?");
////        System.out.println("1. 네.");
////        System.out.println("2. 아니오.");
////        System.out.print("선택 : ");
////
////        valid = false;
////        while (!valid) {
////            try {
////                int choice = scanner.nextInt();
////                scanner.nextLine();
////                switch (choice) {
////                    case 1 -> {
////                        ticketsService.cancelTicket(ticketId);
////                        System.out.println("\n예매가 취소되었습니다.");
////                    }
////                    case 2 -> showMenu(totalUserId);
////                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
////                }
////                valid = true;
////            } catch (InputMismatchException e) {
////                System.out.println("잘못된 입력입니다. 번호를 선택해주세요.");
////                System.out.print("선택 : ");
////                scanner.nextLine();
////            }
////        }
////    }
////
////}
//
//    // 1개의 취소할 예매 내역 가져오기
////    public Tickets getTicketById(List<Tickets> tickets) throws SQLException {
////        String ticketLine = "";
////        int ticketIndex = 0;
////        System.out.println("\n===== 취소할 번호 선택 =====");
////        while (true) {
////            try {
////                System.out.print("취소할 번호 : ");
////                ticketLine = scanner.nextLine();
////                ticketIndex = Integer.parseInt(ticketLine) - 1;
////                // 인덱스 오류 잡기
////                if (ticketIndex >= 0 && ticketIndex < tickets.size()) {
////                    break;
////                } else {
////                    System.out.println("취소할 예매 번호가 없습니다. 다시 입력해주세요.");
////                }
////            } catch (InputMismatchException | NumberFormatException ime) {
////                System.out.println("잘못 입력하셨습니다. 번호로 입력해주세요.");
////            }
////        }
////        return ticketsService.getTicketsById(tickets.get(ticketIndex).getTicketId());
////    }
//
