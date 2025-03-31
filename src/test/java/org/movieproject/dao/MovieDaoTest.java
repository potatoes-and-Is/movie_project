//package org.movieproject.dao;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.movieproject.config.JDBCConnection;
//import org.movieproject.model.Schedules;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//class MovieDaoTest {
//
//    private Connection connection;
//    private ScheduleDao movieDao;
//
//    /**
//     * 📌 테스트 전 DB 연결 및 트랜잭션 설정
//     */
//    @BeforeEach
//    void setUp() {
//        try {
//            connection = JDBCConnection.getConnection();
//            connection.setAutoCommit(false); // 테스트 후 롤백을 위해 AutoCommit 비활성화
//            movieDao = new ScheduleDao(connection);
//
//            // Arrange: 테스트 데이터 삽입
//            Schedules testMovie = new Schedules(1, "어벤져스", 15000, 1,"8시");
//            movieDao.addMovies(testMovie);
//
////            // 삽입된 데이터 확인 (테스트용 ID 저장)
////            List<User> users = userDao.getAllUsers();
////            // 마지막 사용자를 조회함.
////            testUserId = users.get(users.size() - 1).getUserId();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("테스트 데이터 준비 중 오류 발생: " + e.getMessage());
//        }
//    }
//
//    /**
//     * 📌 영화 추가 테스트 (CREATE)
//     */
//    @Test
//    @DisplayName("영화 추가 테스트")
//    void testAddMovie() throws SQLException {
//        // Arrange: 새로운 사용자 객체 생성
//        Schedules newMovie = new Schedules(1, "어벤져스", 15000, 1, "8시");
//
//        // Act: 영화 추가
//        boolean isAdded = movieDao.addMovies(newMovie);
//
//        // Assert: 추가 확인
//        Assertions.assertTrue(isAdded, "사용자가 성공적으로 추가되어야 합니다.");
//    }
//
//    /**
//     * 📌 영화 목록 조회 테스트 (READ)
//     */
//    @Test
//    @DisplayName("모든 영화 조회 테스트")
//    void testGetAllMovies() throws SQLException {
//        // Act: DAO를 통해 데이터 조회
//        List<Schedules> movies = movieDao.getAllMoviesSchedule();
//        System.out.println(movies);
//
//        // Assert: 결과 검증
//        Assertions.assertNotNull(movies, "사용자 목록은 null이 아니어야 합니다.");
//        Assertions.assertFalse(movies.isEmpty(), "사용자 목록은 비어 있지 않아야 합니다.");
//    }
//
//}