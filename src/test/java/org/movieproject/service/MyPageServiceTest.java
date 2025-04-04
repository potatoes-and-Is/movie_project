package org.movieproject.service;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;
import org.movieproject.dao.MyPageDAO;
import org.movieproject.model.Tickets;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyPageServiceTest {

    private Connection connection;
    private MyPageService myPageService;
    private int testUserId = 1;   // 테스트할 사용자 ID
    private int testTicketId = 5; // 테스트할 티켓 ID

    @BeforeEach
    void setUp() throws SQLException {
        connection = JDBCConnection.getConnection();
        connection.setAutoCommit(false); // 테스트 후 롤백
        myPageService = new MyPageService(connection);
    }

    @Test
    void getTicketsByUserId() throws SQLException {
        List<Tickets> tickets = myPageService.getTicketsByUserId(testUserId);
        Assertions.assertNotNull(tickets, "티켓 목록이 null이 아니어야 한다.");
        Assertions.assertFalse(tickets.isEmpty(), "티켓 목록이 비어 있지 않아야 한다.");
        Assertions.assertEquals(1, tickets.get(0).getTicketId());
    }

    @Test
    void getTicketById() throws SQLException {
        Tickets ticket = myPageService.getTicketById(testTicketId);
        Assertions.assertNotNull(ticket, "티켓 상세 정보가 null이 아니어야 한다..");
        Assertions.assertEquals("괴물", ticket.getMovieTitle(), "영화 제목이 일치해야 한다.");
    }

    @AfterEach
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