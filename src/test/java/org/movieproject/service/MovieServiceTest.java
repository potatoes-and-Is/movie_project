package org.movieproject.service;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;
import org.movieproject.dao.ScheduleDao;
import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;

import java.sql.*;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieServiceTest {

    private Connection connection;
    private MovieService movieService;
    private int testMovieId;

    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false); // rollback after test
            movieService = new MovieService(connection);

            // Arrange: insert movie
            String insertMovie = "INSERT INTO movies (movie_title, movie_price) VALUES ('인셉션', 12000)";
            PreparedStatement ps1 = connection.prepareStatement(insertMovie, Statement.RETURN_GENERATED_KEYS);
            ps1.executeUpdate();
            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                testMovieId = rs.getInt(1);
            }
            rs.close();
            ps1.close();

            // Arrange: insert schedule
            String insertSchedule = "INSERT INTO schedules (schedule_start_time, movie_id) VALUES ('14:30', ?)";
            PreparedStatement ps2 = connection.prepareStatement(insertSchedule);
            ps2.setInt(1, testMovieId);
            ps2.executeUpdate();
            ps2.close();

        } catch (SQLException e) {
            throw new RuntimeException("테스트 데이터 준비 중 오류 발생: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("모든 영화 스케줄 조회 테스트")
    void testGetAllMovies() throws SQLException {
        // Act
        List<Schedules> schedules = movieService.getAllMovies();

        // Assert
        Assertions.assertNotNull(schedules, "스케줄 목록이 null이면 안 됩니다.");
        Assertions.assertFalse(schedules.isEmpty(), "스케줄 목록이 비어 있으면 안 됩니다.");
    }

    @Test
    @DisplayName("영화 ID로 영화 정보 조회 테스트")
    void testGetMovieById() throws SQLException {
        // Act
        Movies movie = movieService.getMovieById(testMovieId);

        // Assert
        Assertions.assertNotNull(movie, "영화 정보가 null이면 안 됩니다.");
        Assertions.assertEquals("인셉션", movie.getMovieTitle(), "영화 제목이 일치해야 합니다.");
        Assertions.assertEquals(12000, movie.getMoviePrice(), "영화 가격이 일치해야 합니다.");
    }

    @AfterEach
    void tearDown() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
            System.out.println("🎯 테스트 완료: 데이터 롤백 성공");
        } catch (SQLException e) {
            System.err.println("❌ 테스트 종료 중 오류 발생: " + e.getMessage());
        }
    }
}






