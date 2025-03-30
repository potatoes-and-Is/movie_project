package org.movieproject.view;

import org.movieproject.model.Seat;
import org.movieproject.service.SeatsService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class SeatsView {

    private final SeatsService seatsService;
    private final Scanner scanner;

    public SeatsView(Connection connection, Scanner scanner) {
        // 항상 Connection과 Scanner를 생성자로 받아 초기화합니다.
        this.seatsService = new SeatsService(connection);
        this.scanner = scanner;
    }

    // 선택된 상영시간(scheduleId)에 따른 예약 가능한 좌석을 조회한 후 선택 받는 메서드
    public int chooseSeat(int scheduleId) {
        List<Seat> availableSeats = seatsService.getSeatsByScheduleId(scheduleId);
        System.out.println("===== 예약 가능한 좌석 =====");
        if (availableSeats == null || availableSeats.isEmpty()) {
            System.out.println("예약 가능한 좌석이 없습니다.");
        }
        // 모든 좌석 출력
        for (Seat seat : availableSeats) {
            System.out.print(seat.getSeatNumber() + " ");
        }
        System.out.println();
        System.out.print("예약할 좌석 입력: ");
        String selectedSeat = scanner.nextLine().trim();
        if (selectedSeat.isEmpty()) {
            System.out.println("입력이 없습니다. 좌석을 다시 선택해주세요.");
        }
        // 입력받은 좌석 번호와 일치하는 좌석 검색
        for (Seat seat : availableSeats) {
            if (seat.getSeatNumber() != null && seat.getSeatNumber().equalsIgnoreCase(selectedSeat)) {
                return seat.getSeatId();
            }
        }
        System.out.println("해당 좌석을 찾을 수 없습니다.");
        return -1;
    }

}
