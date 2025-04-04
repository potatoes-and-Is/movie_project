package org.movieproject.dao;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Tickets;

import java.sql.Connection;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketsDaoTest {

    private Connection connection;
    private TicketsDao ticketsDao;
    private Tickets ticket;

    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            ticketsDao = new TicketsDao(connection);
        } catch (SQLException e) {
            Assertions.fail("데이터베이스 연결 실패");
        }
    }

    @Test
    @DisplayName("티켓 ID별 티켓 정보 조회")
    void getTicketsByIdTest() {
        ticket = ticketsDao.getTicketsById(27);
        System.out.println("조회된 예매 건: " + ticket.getTicketId());

        Assertions.assertNotNull(ticket, "조회한 Tickets 객체가 null이 아니어야 합니다.");
        Assertions.assertEquals(27, ticket.getTicketId(), "조회한 티켓의 ID가 삽입한 ID와 일치해야 합니다.");
        Assertions.assertEquals('N', ticket.getCancelStatus(), "조회한 티켓의 예매 취소 여부가 일치해야 합니다.");
    }

    @Test
    @DisplayName("티켓 ID로 예매 취소 여부 컬럼값 변경")
    void cancelTicketTest() {
        boolean updateTicket = ticketsDao.cancelTicket(ticket);
        System.out.println("변경된 예매 건: " + ticket.getTicketId());

        Assertions.assertTrue(updateTicket);
    }

    @Test
    void tearDown() {
        try {
            connection.close();
            System.out.println("테스트 데이터 롤백 완료");
        } catch (SQLException e) {
            Assertions.fail("트랜잭션 롤백 실패" + e.getMessage());
        }
    }
}