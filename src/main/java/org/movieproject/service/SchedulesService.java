package org.movieproject.service;

import org.movieproject.dao.SchedulesDao;
import org.movieproject.model.Schedules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SchedulesService {
    // 로그 남기기 위한 객체 생성
    private static final Logger log = LoggerFactory.getLogger(SchedulesService.class);

    private final Connection connection;
    private final SchedulesDao schedulesDao;

    public SchedulesService(Connection connection) {
        this.connection = connection;
        this.schedulesDao = new SchedulesDao(connection);
    }

    // 특정 영화 ID의 상영 시간 조회
    public List<Schedules> getScheduleByMovieId(int movieId) throws SQLException {
        List<Schedules> schedules = schedulesDao.getSchedulesBymovieId(movieId);
        if(schedules == null){
            throw new IllegalArgumentException("해당 영화의 상영시간을 찾을 수 없습니다.");
        }
        return schedules;
    }
}
