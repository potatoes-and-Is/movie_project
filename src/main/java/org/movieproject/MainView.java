package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.model.User;
import org.movieproject.service.UserService;
import org.movieproject.view.MovieView;
import org.movieproject.view.ScheduleView;
import org.movieproject.view.SeatsView;
import org.movieproject.view.PaymentView;
import org.movieproject.view.TicketView;
import org.movieproject.view.UserView;

import java.sql.Connection;
import java.util.Scanner;

public class MainView {
    public static void main(String[] args) {
        try (Connection conn = JDBCConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            // 1. 회원가입/로그인 (UserView)
            UserService userService = new UserService(conn); // 의존성 주입
            UserView userView = new UserView(userService, scanner);

            User loggedInUser = userView.displayUserMenu();
            if (loggedInUser == null) {
                return; // 프로그램 종료
            }
            System.out.println("로그인 성공! 환영합니다, " + loggedInUser.getNickname() + "님!");

            while (true) {
                // 2. 영화 선택 (MovieView)
                MovieView movieView = new MovieView(conn, scanner);
                int selectedMovieId = movieView.displayMovies(); // 영화 ID 반환

                // 3. 시간 선택 (ScheduleView)
                ScheduleView scheduleView = new ScheduleView(conn, scanner);
                int selectedScheduleId = scheduleView.displaySchedules(selectedMovieId); // 상영시간 ID 반환

                if (selectedScheduleId == -1) {
                    continue; // 루프의 처음으로 돌아가서 영화 선택부터 다시 시작
                }

                // 4. 좌석 선택 (SeatsView)
                SeatsView seatsView = new SeatsView(conn, scanner);
                int selectedSeatId = -1; // 초기값 설정

                while (selectedSeatId == -1) {
                    selectedSeatId = seatsView.chooseSeat(selectedScheduleId); // 좌석 ID 반환
                    if (selectedSeatId == -1) {
                        System.out.println("유효하지 않은 좌석입니다. 다시 선택해주세요.");
                    }
                }

                // 5. 결제 처리 (PaymentView)
                PaymentView paymentView = new PaymentView(conn, scanner);
                int paymentId = -1; // 초기값 설정
                while (paymentId == -1) {
                    paymentId = paymentView.processPayment(); // 결제 ID 반환
                }

                // 6. 티켓 생성 (TicketView)
                TicketView ticketView = new TicketView(conn);
                ticketView.createTicket(selectedScheduleId, selectedSeatId, loggedInUser.getUserId(), paymentId);

                // 루프 종료 여부 확인
                System.out.print("다른 영화를 예매하시겠습니까?");
                System.out.print("1. 예");
                System.out.println("2. 아니오");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 2) {
                    System.out.println("프로그램을 종료합니다.");
                    break; // 루프 종료
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
