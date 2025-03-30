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

    private static final Logger log = LoggerFactory.getLogger(SeatsService.class);

    private SeatsDao seatsDao;

    public SeatsService(Connection connection) {
        this.seatsDao = new SeatsDao(connection);
    }

    // 특정 스케줄에 대하여 예약 가능한 좌석 반환
    public List<Seat> getSeatsByScheduleId(int scheduleId) {
        try {
            List<Seat> allSeats = seatsDao.getAllSeats();
            List<Seat> unavailableSeats = seatsDao.getUnavailSeats(scheduleId);
            List<Seat> availableSeats = new ArrayList<>();
            if (unavailableSeats == null || unavailableSeats.isEmpty()) {
                availableSeats = allSeats;
            } else {
                allSeats.removeAll(unavailableSeats); // equals와 hashCode가 올바르게 구현되어 있어야 함
                availableSeats.addAll(allSeats);
            }
            return availableSeats;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
