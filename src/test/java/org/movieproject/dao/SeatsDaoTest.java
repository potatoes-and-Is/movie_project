package org.movieproject.dao;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Movies;
import org.movieproject.model.Seats;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeatsDaoTest {

    private Connection connection;
    private SeatsDao dao;
    private int scheduleId = 1;

    @BeforeAll
    void setUp() throws SQLException {
        connection = JDBCConnection.getConnection();
        connection.setAutoCommit(false); // 테스트 후 롤백
        dao = new SeatsDao(connection);
    }

    // 좌석 목록 조회 테스트
    @Test
    void getAllSeats() {
        List<Seats> seats = dao.getAllSeats();

        Assertions.assertNotNull(seats, "좌석 목록은 null이 아니어야 합니다.");
        Assertions.assertFalse(seats.isEmpty(), "좌석 목록은 비어있지 않아야 합니다.");
    }

    // 예약 불가능한 좌석 목록 조회 테스트
    @Test
    void getUnavailSeats() {
        List<Seats> seat = dao.getUnavailSeats(scheduleId);
        System.out.println("예약이 불가능한 좌석의 첫번째ID : " + seat.get(0).getSeatId());

        Assertions.assertNotNull(seat, "예약 불가능한 좌석이 null이 아니어야 합니다.");
        Assertions.assertEquals(3, seat.get(0).getSeatId(), "예약이 불가능한 좌석 ID가 일치해야 합니다.");
    }

    // 좌석번호로 좌석 정보 가져오기 테스트
    @Test
    void getSeatBySeatNumber() {
        Seats seat = dao.getSeatBySeatNumber("A-1");
        System.out.println("조회된 좌석id : " + seat.getSeatId() + ", 조회된 좌석번호 : " + seat.getSeatNumber());

        Assertions.assertNotNull(seat, "조회된 Seat 객체가 null이 아니어야 합니다.");
        Assertions.assertEquals(1, seat.getSeatId(), "조회된 좌석의 ID가 삽입한 ID와 일치해야 합니다.");
        Assertions.assertEquals("A-1", seat.getSeatNumber(), "조회된 좌석의 번호가 일치해야 합니다.");
    }

    // scheduleId 로 영화 제목과 상영시간 불러오기 테스트
    @Test
    void getCinemaInfoByScheduleId() {
        Movies movie = dao.getCinemaInfoByScheduleId(scheduleId);
        System.out.println("조회된 영화 제목 : " + movie.getMovieTitle() + ", 조회된 영화 상영 시간 : " + movie.getScheduleStartTime());

        Assertions.assertNotNull(movie, "조회된 영화 객체가 null이 아니어야 합니다.");
        Assertions.assertEquals("미키 17", movie.getMovieTitle(), "조회된 영화의 제목이 같아야 합니다.");
        Assertions.assertEquals("10:00", movie.getScheduleStartTime(), "조회된 영화의 상영시간이 같아야 합니다.");
    }

    @AfterAll
    void tearDown() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
        System.out.println("테스트 완료 : 데이터 롤백 성공");
    }
}