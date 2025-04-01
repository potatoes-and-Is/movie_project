package org.movieproject.service;

import org.movieproject.dao.SeatsDao;
import org.movieproject.model.Movies;
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

    // schedule_id로 영화 제목과 상영시간 가져오기
    public String getMoviesbyScheduleId(int scheduleId) {

        Movies movie = seatsDao.getCinemaInfoByScheduleId(scheduleId);

        if(movie == null) {
            System.out.println("해당 영화 정보가 없습니다.");
            return null;
        }

        return "시간 : " + movie.getScheduleStartTime() + "시 \n영화 : " + movie.getMovieTitle();
    }

    // Cinema_infos 테이블 add, cinema_info_id 반환
    public int addCinemaInfo(int ScheduleId, String seatNumber) {

        Seats seat = seatsDao.getSeatBySeatNumber(seatNumber);

        if(seat == null) {
            System.out.println("해당 좌석 번호로 조회된 seat이 없습니다.");
            return 0;
        }
        int seatId = seat.getSeatId();
        return seatsDao.addCinemaInfo(ScheduleId, seatId);
    }
}
