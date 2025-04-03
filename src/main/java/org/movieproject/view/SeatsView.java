package org.movieproject.view;

import org.movieproject.model.Seats;
import org.movieproject.model.Users;
import org.movieproject.service.SeatsService;
import org.movieproject.service.TicketsService;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SeatsView {
    private final SeatsService seatsService;
    private final Scanner scanner;
    private final TicketsView ticketsView;
    private final PaymentView paymentView;

    private static final Integer COLUMN_MAX = 5;

    public SeatsView(Connection connection) {
        this.seatsService = new SeatsService(connection);
        this.ticketsView = new TicketsView(connection);
        this.paymentView = new PaymentView(connection);
        this.scanner = new Scanner(System.in);
    }

    // 선택 가능한 좌석 출력
    public void showSeats(int scheduleChoice) {
        System.out.println("\n===== 다음은 예약 가능한 좌석입니다. =====");
        try {
            int cnt = 0;
            List<Seats> seats = seatsService.getSeatsbyScheduleId(scheduleChoice);
            for (int i = 0; i < (seats.size() / COLUMN_MAX.floatValue()); i++) {
                for (int j = 0; j < COLUMN_MAX; j++) {
                    if (cnt > seats.size() - 1) {
                        break;
                    } else {
                        System.out.print("[" + seats.get(cnt).getSeatNumber() + "]   ");
                        cnt++;
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("showSeats:" + e.getMessage());
        }
    }

    public void selectSeat(int scheduleChoice, Users loginUser) {
        try {
            while (true) {
                System.out.println("\n===== 관람을 원하시는 좌석 번호를 선택해주세요. =====");
                String seatChoice = scanner.nextLine();

                // 잘못된 좌석 번호를 입력한 경우
                if (!isValidSeat(seatChoice)) {
                    System.out.println("잘못된 형식이거나 존재하지 않는 좌석 번호입니다. 다시 입력해주세요.");
                    continue;
                }

                // 사용자가 선택한 좌석이 이미 예약되어있는 좌석일 경우
                if (!isAvailSeats(scheduleChoice, seatChoice)) {
                    System.out.println("이미 예약되어 있는 좌석 번호입니다. 다른 좌석을 선택해주세요.");
                    continue;
                }

                String movieInfo = seatsService.getMoviesbyScheduleId(scheduleChoice);
                System.out.println("\n===== 다음은 선택하신 영화와 좌석 정보입니다. =====");
                System.out.println(movieInfo);
                System.out.println("좌석 : " + seatChoice);

                System.out.println("\n===== 결제를 진행하시겠습니까? =====");
                System.out.println("1. 예");
                System.out.println("2. 아니오");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> { // 결제 진행을 눌렀을 때 cinema_info 테이블에 insert 후 cinema_info_id 반환
                        int result = seatsService.addCinemaInfo(scheduleChoice, seatChoice); // cinema_info_id
                        int ticketId = ticketsView.saveTicket(result, loginUser.getUserId());
                        paymentView.showPaymentMenu(ticketId, loginUser);
                        return;
                    }
                    case 2 -> {
                        System.out.println("메인 화면으로 돌아갑니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            }
        } catch (Exception e) {
            System.out.println("selectSeat:" + e.getMessage());
        }
    }

    // 좌석 번호가 올바른 형식인지 확인하는 메서드
    private boolean isValidSeat(String seat) {
        // A-1, B-10, C-25 형식과 같은 패턴을 맞추기 위한 정규식
        String seatPattern = "^[A-D]-[1-5]"; // A-1 ~ Z-99 형식
        return Pattern.matches(seatPattern, seat);
    }

    // 좌석이 이미 예약되어 있는 좌석인지 확인하는 메서드
    private boolean isAvailSeats(int scheduleChoice, String seatNumber) {
        List<Seats> unavailSeats = seatsService.getUnavilSeatsbyScheduleId(scheduleChoice);
        for(int i=0; i<unavailSeats.size(); i++) {
            if(unavailSeats.get(i).getSeatNumber().equals(seatNumber)) {
                return false;
            }
        }
        return true;
    }
}


