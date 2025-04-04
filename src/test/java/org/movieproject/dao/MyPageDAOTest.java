package org.movieproject.dao;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Tickets;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyPageDAOTest {

    private Connection connection;
    private MyPageDAO dao;
    private int testUserId = 1;   // 테스트할 사용자 ID
    private int testTicketId = 5; // 테스트할 티켓 ID

    @BeforeAll
    void setUp() throws SQLException {
        connection = JDBCConnection.getConnection();
        connection.setAutoCommit(false); // 테스트 후 롤백
        dao = new MyPageDAO(connection);
    }

    // 사용자 ID로 티켓 목록 조회
    @Test
    void getTicketsByUserId() {
        List<Tickets> tickets = dao.getTicketsByUserId(testUserId);

        Assertions.assertNotNull(tickets, "티켓 목록이 null이 아니어야 한다.");
        Assertions.assertFalse(tickets.isEmpty(), "티켓 목록이 비어 있지 않아야 한다.");

        // 첫 번째 티켓이 존재하는 경우, 기본적인 검증 수행
        Tickets firstTicket = tickets.get(0);
        Assertions.assertNotNull(firstTicket.getMovieTitle(), "영화 제목이 null이 아니어야 한다.");
        Assertions.assertNotNull(firstTicket.getScheduleStartTime(), "상영 시간이 null이 아니어야 한다.");
    }

    // 티켓 ID로 상세 정보 조회
    @Test
    void getTicketById() {
        Tickets ticket = dao.getTicketById(testTicketId);

        Assertions.assertNotNull(ticket, "티켓이 null이 아니어야 한다.");
        Assertions.assertEquals(testTicketId, ticket.getTicketId(), "조회된 티켓 ID가 일치해야 한다.");
        Assertions.assertNotNull(ticket.getUserNickname(), "사용자 닉네임이 null이 아니어야 한다.");
        Assertions.assertNotNull(ticket.getMovieTitle(), "영화 제목이 null이 아니어야 한다.");
        Assertions.assertNotNull(ticket.getScheduleStartTime(), "상영 시간이 null이 아니어야 한다.");
        Assertions.assertNotNull(ticket.getSeatNumber(), "좌석 번호가 null이 아니어야 한다.");
    }

    @AfterAll
    void tearDown() throws SQLException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
            System.out.println("테스트가 무사히 통과하였습니다.");
        }catch (SQLException e) {
            System.out.println("테스트가 비정상적으로 종료되었습니다.");
        }
    }
}
