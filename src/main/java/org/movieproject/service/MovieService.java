package org.movieproject.service;

import org.movieproject.dao.MyPageDao;
import org.movieproject.dao.ScheduleDao;
import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;
import org.movieproject.model.Tickets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    private final ScheduleDao scheduleDao;
    private final MyPageDao myPageDao;
    private final Connection connection;

    /**
     * 📌 생성자 주입 (의존성 주입)
     */
    public MovieService(Connection connection) {
        this.connection = connection;
        this.scheduleDao = new ScheduleDao(connection);
        this.myPageDao = new MyPageDao(connection);
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

    /* 회원별 티켓 정보 간단히 출력 */
    public List<Tickets> getTicketsByUserId(int userId) throws SQLException {
        return myPageDao.getTicketsByUserId(userId);
    }

    /* 티켓 상세 정보 출력 */
    public Tickets getTicketById(int ticketId) throws SQLException {
        // MyPageDao 인스턴스를 통해 메서드 호출
        Tickets ticketInfo = myPageDao.getTicketById(ticketId);
        if (ticketInfo == null) {
            log.error("티켓 ID가 존재하지 않습니다.");
            return null;
        }
        return ticketInfo;
    }


}
