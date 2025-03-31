package org.movieproject.service;

import org.movieproject.dao.ScheduleDao;
import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    private final ScheduleDao scheduleDao;
    private final Connection connection;

    /**
     * 📌 생성자 주입 (의존성 주입)
     */
    public MovieService(Connection connection) {
        this.connection = connection;
        this.scheduleDao = new ScheduleDao(connection);
    }

    // 모든 영화 출력
    public List<Schedules> getAllMovies() throws SQLException {
        List<Schedules> schedules = scheduleDao.getAllMoviesSchedule();

        if(schedules == null) {
            log.error("조회한 영화의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            return null;
        }

        return schedules;
    }

    // 영화ID 값으로 한개 영화 조회
    public Movies getMovieById(int movieId) throws SQLException {
        Movies movie = scheduleDao.getMovieById(movieId);

        if(movie == null) {
            log.error("조회한 영화의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            return null;
        }
        return movie;
    }


}
