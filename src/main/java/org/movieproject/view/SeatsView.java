package org.movieproject.view;

import org.movieproject.dao.CinemaInfoDao;
import org.movieproject.model.Seat;
import org.movieproject.service.CinemaInfoService;
import org.movieproject.service.SeatsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SeatsView {

    private final SeatsService seatsService;
    private final Scanner scanner;

    // Connection과 Scanner를 생성자로 받아 초기화
    public SeatsView(Connection connection, Scanner scanner) {
        this.seatsService = new SeatsService(connection);
        this.scanner = scanner;
    }

    // 선택된 상영시간(scheduleId)에 따른 예약 가능한 좌석을 조회한 후 선택 받는 메서드
    public int chooseSeat(int scheduleId) throws SQLException {
        List<Seat> availableSeats = seatsService.getSeatsByScheduleId(scheduleId);
        Set<String> reservedSeats = seatsService.reservedSeats(scheduleId);

        System.out.println("===== 예약 가능한 좌석(뒤로가기: 0) ======");
        if (availableSeats.isEmpty()) { // 좌석 예약이 이미 다 되었을 때
            System.out.println("예약 가능한 좌석이 없습니다.");
        }
        printAvailableSeats(availableSeats); // 예약 가능한 좌석 출력

        return seatReservation(availableSeats, reservedSeats); // 예약 가능한 좌석 반환
    }

    private int seatReservation(List<Seat> availableSeats, Set<String> reservedSeats) {
        System.out.print("예약할 좌석 입력: ");
        String selectedSeat = scanner.nextLine().trim();
        if (selectedSeat.equals("0")) {
            return 0;
        }
        if (selectedSeat.isEmpty()) {
            System.out.println("잘못 입력하였습니다. 좌석을 다시 선택해주세요.");
            return -1;
        }
        for (String reservedSeat : reservedSeats) {
            if (reservedSeat.equalsIgnoreCase(selectedSeat)) {
                System.out.println("이미 예약된 좌석입니다. 좌석을 다시 선택해주세요.");
                return -1;
            }
        }
        // 입력받은 좌석 번호와 일치하는 좌석 검색
        for (Seat seat : availableSeats) {
            if (!seat.getSeatNumber().trim().isEmpty() && seat.getSeatNumber().equalsIgnoreCase(selectedSeat)) {
                return seat.getSeatId();
            }
        }
        System.out.println("유효한 좌석이 아닙니다. 좌석을 다시 선택해주세요.");
        return -1;
    }

    private static void printAvailableSeats(List<Seat> availableSeats) {
        // 모든 좌석 출력
        int cnt = 0;
        while (cnt < availableSeats.size()) {
            for (int j = 0; j < 5 && cnt < availableSeats.size(); j++) {
                System.out.print(availableSeats.get(cnt).getSeatNumber() + "  ");
                cnt++;
            }
            System.out.println();
        }
    }
}
