//package org.movieproject.view;
//
//import org.movieproject.model.Tickets;
//import org.movieproject.service.ReservationsService;
//import org.movieproject.service.TicketsService;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Scanner;
//
//public class TicketsView {
//
//    private final TicketsService ticketsService;
//    private final ReservationsService reservationsService;
//    private final Scanner scanner;
//    int userId = 1;
//    boolean valid = false;
//
//    public TicketsView(Connection connection) {
//        this.ticketsService = new TicketsService(connection);
//        this.reservationsService = new ReservationsService(connection);
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void showMyPage(int userId) throws SQLException {
//        // 예매 내역 리스트
//        List<Tickets> tickets = ticketsService.getTicketsByUserId(userId);
//        Tickets ticketById;
//        boolean emptyFlag = true;
//
//        // 예매 내역이 없을 경우
//        if (tickets.isEmpty()) {
//            System.out.println("예매 내역이 없습니다.");
//            System.out.println("<1> 이전으로");
//        } else {
//            // 예매 내역이 있을 경우
//            System.out.println("\n===== 예매 내역 =====");
//            int num = 1;
//            for (Tickets ticket : tickets) {
//                System.out.println(num++ + ". " + ticket.getTicketId());
//            }
//
//            // 예매 건 취소하기 및 이전으로
//            emptyFlag = false;
//            System.out.println("<1> 예매 건 취소하기");
//            System.out.println("<2> 이전으로");
//            System.out.print("선택 : ");
//        }
//
//        valid = false;
//        while (!valid) {
//            try {
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//
//                switch (choice) {
//                    case 1 -> {
//                        if (emptyFlag) {
//                            System.out.println("mainPage로 ~");
//                            return;
//                        } else {
//                            ticketById = getTicketById(tickets);
//                            cancelTicket(ticketById);
//                        }
//                    } // 1개의 취소할 예매 내역 가져오기
//                    case 2 -> {
//                        System.out.println("mainPage로 ~");
//                        return;
//                    }
//                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
//                }
//                valid = true;
//            } catch (InputMismatchException e) {
//                System.out.println("잘못된 입력입니다. 번호를 선택해주세요.");
//                System.out.print("선택 : ");
//                scanner.nextLine();
//            }
//        }
//    }
//
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
//
//    public void cancelTicket(int ticketById) throws SQLException {
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
//                        ticketsService.cancelTicket(ticketById);
//                        System.out.println("\n예매가 취소되었습니다.");
//                    }
//                    case 2 -> showMyPage(userId);
//                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
//                }
//                valid = true;
//            } catch (InputMismatchException e) {
//                System.out.println("잘못된 입력입니다. 번호를 선택해주세요.");
//                System.out.print("선택 : ");
//                scanner.nextLine();
//            }
//        }
//    }
//}