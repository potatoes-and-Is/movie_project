package org.movieproject.service;

import org.movieproject.dao.SeatsDao;
import org.movieproject.model.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatsService {

    private final SeatsDao seatsDao;

    public SeatsService(Connection connection) {
        this.seatsDao = new SeatsDao(connection);
    }

    // 특정 스케줄에 대하여 예약 가능한 좌석 반환
    public List<Seat> getSeatsByScheduleId(int scheduleId) {
        try {
            List<Seat> allSeats = seatsDao.getAllSeats();
            List<Seat> unavailableSeats = seatsDao.getUnavailSeats(scheduleId);

            if (unavailableSeats == null || unavailableSeats.isEmpty()) {
                return allSeats; // 좌석이 다 있으면 allSeats 반환
            } else {
                for (int i = 0; i < allSeats.size(); i++) {
                    Seat seat = allSeats.get(i);
                    if(unavailableSeats.contains(seat)) {
                        // 만약 Seat 클래스가 set메서드를 지원한다면 seat.setSeatNumber(""); 와 같이 처리할 수도 있음
                        // 여기서는 새로운 빈 객체로 교체하는 예시입니다.
                        allSeats.set(i, new Seat("  "));
                    }
                }
            }
            return allSeats;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
