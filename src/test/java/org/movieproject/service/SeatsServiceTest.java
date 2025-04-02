package org.movieproject.service;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Movies;
import org.movieproject.model.Seats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeatsServiceTest {
    private static final Logger log = LoggerFactory.getLogger(SeatsServiceTest.class);
    private Connection connection;
    private SeatsService service;
    private int scheduleId = 1;

    @BeforeEach
    void setUp() throws SQLException {
        connection = JDBCConnection.getConnection();
        connection.setAutoCommit(false); // 자동 커밋 방지
        service = new SeatsService(connection);
    }

    // 해당 스케줄에 예약 가능한 좌석 가져오기
    @Test
    void getSeatsbyScheduleId() throws SQLException {
        List<Seats> seats = service.getSeatsbyScheduleId(scheduleId);

        Assertions.assertNotNull(seats, "해당 스케줄에 예약 가능한 좌석은 null이면 안됩니다.");
        Assertions.assertFalse(seats.isEmpty(), "해당 스케줄에 예약 가능한 좌석은 비어있지 않아야 합니다.");
        Assertions.assertEquals(1, seats.get(0).getSeatId(), "예약 가능한 좌석이 일치해야 합니다.");
    }

    // 해당 스케줄에 예약이 불가능한 좌석 가져오기
    @Test
    void getUnavilSeatsbyScheduleId() throws SQLException {
        List<Seats> seats = service.getUnavilSeatsbyScheduleId(scheduleId);

        Assertions.assertNotNull(seats, "해당 스케줄에 예약 불가능한 좌석은 null이면 안됩니다.");
        Assertions.assertFalse(seats.isEmpty(), "해당 스케줄에 예약 불가능한 좌석이 비어있지 않아야 합니다.");
        Assertions.assertEquals(3, seats.get(0).getSeatId(), "예약 불가능한 좌석이 일치해야 합니다.");
    }

    // scheduleId로 영화 제목과 상영시간 가져오기
    @Test
    void getMoviesbyScheduleId() throws SQLException {
        String movie = service.getMoviesbyScheduleId(scheduleId);

        Assertions.assertNotNull(movie, "영화 정보가 null이면 안됩니다.");
        Assertions.assertEquals("시간 : 10:00시 \n" +
                "영화 : 미키 17", movie, "영화 시간과 제목이 일치해야 합니다.");
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
        log.info("테스트 종료 후 데이터 롤백 완료");
    }
}