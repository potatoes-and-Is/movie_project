package org.movieproject.dao;

import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스 단위로 인스턴스 유지
class ScheduleDaoTest {

    private Connection connection;
    private ScheduleDao scheduleDao;

    private int testMovieId;


    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false); // 테스트 후 롤백을 위해 AutoCommit 비활성화
            scheduleDao = new ScheduleDao(connection);

            // Arrange: 테스트용 영화 데이터 삽입
            String insertMovie = "INSERT INTO movies (movie_title, movie_price) VALUES ('어벤져스', 10000)";
            PreparedStatement ps1 = connection.prepareStatement(insertMovie, Statement.RETURN_GENERATED_KEYS);
            ps1.executeUpdate();
            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                testMovieId = rs.getInt(1);
            }
            rs.close();
            ps1.close();

            // Arrange: 테스트용 스케줄 데이터 삽입
            String insertSchedule = "INSERT INTO schedules (schedule_start_time, movie_id) VALUES ('10:00', ?)";
            PreparedStatement ps2 = connection.prepareStatement(insertSchedule);
            ps2.setInt(1, testMovieId);
            ps2.executeUpdate();
            ps2.close();

        } catch (SQLException e) {
            throw new RuntimeException("테스트 데이터 준비 중 오류 발생: " + e.getMessage());
        }
    }


    @Test
    @DisplayName("모든 스케줄 조회 테스트")
    void testGetAllSchedules() throws SQLException {
        // Act
        List<Schedules> schedules = scheduleDao.getAllMoviesSchedule();

        // Assert
        Assertions.assertNotNull(schedules, "스케줄 목록은 null이 아니어야 합니다.");
        Assertions.assertFalse(schedules.isEmpty(), "스케줄 목록은 비어 있지 않아야 합니다.");
    }

    @Test
    @DisplayName("영화 ID로 영화 조회 테스트")
    void testGetMovieById() throws SQLException {
        // Act
        Movies movie = scheduleDao.getMovieById(testMovieId);

        // Assert
        Assertions.assertNotNull(movie, "해당 ID의 영화는 존재해야 합니다.");
        Assertions.assertEquals("어벤져스", movie.getMovieTitle(), "영화 제목이 일치해야 합니다.");
        Assertions.assertEquals(10000, movie.getMoviePrice(), "영화 가격이 일치해야 합니다.");
    }

    @AfterEach
    void tearDown() {
        try {
            connection.rollback(); // 테스트 데이터 롤백
            connection.setAutoCommit(true);
            connection.close();
            System.out.println("테스트 완료: 데이터 롤백 성공");
        } catch (SQLException e) {
            System.err.println("테스트 종료 중 오류 발생: " + e.getMessage());
        }
    }
}