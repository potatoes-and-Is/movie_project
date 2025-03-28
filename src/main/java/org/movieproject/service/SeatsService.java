package org.movieproject.service;

import org.movieproject.dao.SeatsDao;
import org.movieproject.model.Seats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SeatsService {

    private static final Logger log = LoggerFactory.getLogger(SeatsService.class);

    private final Connection connection;
    private final SeatsDao seatsDao;

    public SeatsService(Connection connection) {
        this.connection = connection;
        this.seatsDao = new SeatsDao(connection);
    }

    // 해당 스케줄에 예약 가능한 좌석 가져오기
    public List<Seats> getSeatsbyScheduleId(int scheduleId) {

        List<Seats> allSeats = seatsDao.getAllSeats();
        List<Seats> unavilSeats = seatsDao.getUnavailSeats(scheduleId);
        List<Seats> availSeats = new ArrayList<>();

        if(unavilSeats == null  || unavilSeats.isEmpty()) {
            availSeats = allSeats;
        } else {
            availSeats = allSeats;
            for (int i = 0; i < availSeats.size(); i++) {
                Seats seat = availSeats.get(i);
                if (unavilSeats.contains(seat)) {
                    availSeats.set(i, new Seats(seat.getSeatId(), "   ")); // 예약된 좌석은 공백으로 대체
                }
            }
        }
        return availSeats;
    }
}
