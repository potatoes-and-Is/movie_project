package org.movieproject.service;

import org.movieproject.dao.SeatsDao;
import org.movieproject.model.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatsService {

    private final SeatsDao seatsDao;

    public SeatsService(Connection connection) {
        this.seatsDao = new SeatsDao(connection);
    }

    // 특정 스케줄에 대하여 예약 가능한 좌석 반환
    public List<Seat> getSeatsByScheduleId(int scheduleId) throws SQLException {
        List<Seat> allSeats = seatsDao.getAllSeats(); // 모든 좌석
        List<Seat> unavailableSeats = seatsDao.getUnavailSeats(scheduleId); // 예약된 좌석

        if (unavailableSeats != null && !unavailableSeats.isEmpty()) {
            for (int i = 0; i < allSeats.size(); i++) {
                Seat seat = allSeats.get(i);
                if (unavailableSeats.contains(seat)) {
                    // 만약 Seat 클래스가 set메서드를 지원한다면 seat.setSeatNumber(""); 와 같이 처리할 수도 있음
                    // 여기서는 새로운 빈 객체로 교체하는 예시
                    allSeats.set(i, new Seat("  "));
                }
            }
        }
        return allSeats; // 좌석이 다 있으면 allSeats 반환
    }


    // 이미 예약된 좌석
    public Set<String> reservedSeats(int scheduleId) throws SQLException {
        Set<String> reservedSeats = new HashSet<>();
        List<Seat> unavailableSeats = seatsDao.getUnavailSeats(scheduleId); // 예약된 좌석

        for (Seat unavailableSeat : unavailableSeats) {
            reservedSeats.add(unavailableSeat.getSeatNumber());
        }
        return reservedSeats;
    }
}