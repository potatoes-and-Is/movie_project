package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.model.TicketInfo;
import org.movieproject.model.User;
import org.movieproject.service.UserService;
import org.movieproject.view.MovieView;
import org.movieproject.view.ScheduleView;
import org.movieproject.view.SeatsView;
import org.movieproject.view.PaymentView;
import org.movieproject.view.TicketView;
import org.movieproject.view.UserView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        try (Connection conn = JDBCConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            User loggedInUser = getUser(conn, scanner);
            if (loggedInUser == null) return; // 프로그램 종료

            // 로그인 성공 시 프로세스
            while (true) {
                System.out.println("1. 마이페이지");
                System.out.println("2. 영화예매");
                System.out.println("3. 로그아웃");
                System.out.print("선택: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> {
                        myPageProcess(conn, scanner, loggedInUser); // 마이 페이지
                    }
                    case "2" -> {
                        movieProcess(conn, scanner, loggedInUser); // 영화 
                    }
                    case "3" -> {
                        return; // 종료
                    }
                    default -> {
                        System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 마이 페이지 프로세스
    private static void myPageProcess(Connection conn, Scanner scanner, User loggedInUser) throws SQLException {
        TicketView ticketView = new TicketView(conn);
        while (true) {
            System.out.println("1. 티켓조회");
            System.out.println("2. 예매취소");
            System.out.println("3. 뒤로가기");
            System.out.print("선택: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    printTicketList(loggedInUser, ticketView);
                }
                case "2" -> {
                    deleteTicket(loggedInUser, scanner, ticketView);
                }
                case "3" -> {
                    System.out.println("영화 예매 프로그램을 종료합니다.");
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            }
        }
    }

    // 티켓 정보 출력 
    private static void printTicketList(User loggedInUser, TicketView ticketView) {
        List<TicketInfo> userTickets = ticketView.getUserTickets(loggedInUser);
        for (TicketInfo userTicket : userTickets) {
            System.out.println(userTicket);
        }
    }

    // 티켓 ID로 삭제
    private static void deleteTicket(User loggedInUser, Scanner scanner, TicketView ticketView) throws SQLException {
        printTicketList(loggedInUser, ticketView);
        while (true) {
            System.out.println("삭제 할 티켓 ID를 입력해주세요.");
            System.out.println("0. 뒤로가기");

            String input = scanner.nextLine();
            int ticketId = Integer.parseInt(input);
            if (ticketId == 0) return;
            if (ticketView.deleteByTicketId(ticketId)) {
                System.out.println("티켓이 삭제 되었습니다.");
                return;
            } else {
                System.out.println("티켓이 삭제되지 않았습니다. 다시 입력해주세요.");
            }
        }
    }

    // 영화 프로세스
    private static void movieProcess(Connection conn, Scanner scanner, User loggedInUser) throws SQLException {
        outer:
        while (true) {
            // 2. 영화 선택 => 영화 ID 반환 (MovieView)
            int selectedMovieId = getSelectedMovieId(conn, scanner);

            // 3. 시간 선택 (ScheduleView)
            ScheduleView scheduleView = new ScheduleView(conn, scanner);

            // 스케쥴 ID
            Integer selectedScheduleId = getScheduleId(scheduleView, selectedMovieId);
            if (selectedScheduleId == null) continue; // 루프의 처음으로 돌아가서 영화 선택부터 다시 시작

            // 4. 좌석 선택 (SeatsView)
            SeatsView seatsView = new SeatsView(conn, scanner);
            int selectedSeatId;

            while (true) {
                selectedSeatId = seatsView.chooseSeat(selectedScheduleId);

                if (selectedSeatId == 0) { // 0 입력 시 상영시간 선택
                    selectedScheduleId = scheduleView.displaySchedules(selectedMovieId);
                    if (selectedScheduleId == -1) {
                        // 상영시간 선택에서 재차 뒤로가거나 상영시간이 없는 경우 영화 선택으로 돌아감
                        continue outer;
                    }
                } else if (selectedSeatId > 0) {
                    // 유효한 좌석 번호가 선택되었으면 루프 종료
                    break;
                }
            }

            // 5. 결제 처리 (PaymentView)
            Integer paymentId = getPaymentId(conn, scanner, selectedMovieId);
            if (paymentId == null) continue outer; // 영화 선택부터 다시

            // 6. 티켓 생성
            createTicket(conn, selectedScheduleId, selectedSeatId, loggedInUser, paymentId);

            // 7. 영화 추가 예매 여부
            if (buyAnotherTicket(scanner)) break;
        }
    }

    // 유저 ID 반환
    private static User getUser(Connection conn, Scanner scanner) throws SQLException {
        // 1. 회원가입/로그인 (UserView)
        UserService userService = new UserService(conn); // 의존성 주입
        UserView userView = new UserView(userService, scanner);

        User loggedInUser = userView.displayUserMenu();
        if (loggedInUser == null) {
            return null;
        }
        return loggedInUser;
    }

    // 영화 ID 반환
    private static int getSelectedMovieId(Connection conn, Scanner scanner) {
        MovieView movieView = new MovieView(conn, scanner);
        int selectedMovieId = movieView.displayMovies(); // 영화 ID 반환
        return selectedMovieId;
    }

    // 스케쥴 ID 반환
    private static Integer getScheduleId(ScheduleView scheduleView, int selectedMovieId) {
        int selectedScheduleId = scheduleView.displaySchedules(selectedMovieId); // 상영시간 ID 반환
        if (selectedScheduleId == -1) {
            return null;
        }
        return selectedScheduleId;
    }

    // 티켓 생성
    private static void createTicket(Connection conn, int selectedScheduleId, int selectedSeatId, User loggedInUser, int paymentId) throws SQLException {
        // 6. 티켓 생성 (TicketView)
        TicketView ticketView = new TicketView(conn);

        ticketView.createTicket(selectedScheduleId, selectedSeatId, loggedInUser.getUserId(), paymentId);
    }

    // 결제 ID 반환
    private static Integer getPaymentId(Connection conn, Scanner scanner, int selectedMovieId) {
        PaymentView paymentView = new PaymentView(conn, scanner);
        while (true) {
            int paymentId = paymentView.processPayment(selectedMovieId);

            if (paymentId > 0) {
                return paymentId; // 유효한 결제 ID가 있으면 반환
            } else if (paymentId == 0) {
                System.out.println("결재가 취소되었습니다. 영화를 다시 선택하여 주시기 바랍니다.");
                return null;     // 결제 취소나 해당하는 상황이면 null 반환
            }
            // paymentId가 -1인 경우 정상 입력이 아니므로 루프를 통해 다시 시도
        }
    }

    // 루프 종료 여부 확인
    private static boolean buyAnotherTicket(Scanner scanner) {
        System.out.print("다른 영화를 예매하시겠습니까?  ");
        System.out.print("1. 예  ");
        System.out.println("2. 아니오");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice == 2) {
            System.out.println("예매 프로그램을 종료합니다.");
            return true;
        }
        return false;
    }

}
