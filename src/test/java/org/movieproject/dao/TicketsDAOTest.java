package org.movieproject.dao;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Tickets;

import java.sql.Connection;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketsDAOTest {

    private Connection connection;
    private TicketsDAO ticketsDAO;

    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            ticketsDAO = new TicketsDAO(connection);
        } catch (SQLException e) {
            Assertions.fail("데이터베이스 연결 실패");
        }
    }


    @Test
    @DisplayName("티켓 ID로 예매 취소 여부 컬럼값 변경")
    void cancelTicketTest() {
        Tickets ticket = ticketsDAO.getTicketsById(27);
        System.out.println("변경된 예매 건: " + ticket.getTicketId());

        Assertions.assertNotNull(ticket, "변경할 Tickets 객체가 null이 아니어야 합니다.");
        Assertions.assertEquals(27, ticket.getTicketId(), "변경된 티켓의 ID가 삽입한 ID와 일치해야 합니다.");
        Assertions.assertEquals('N', ticket.getCancelStatus(), "변경된 티켓의 예매 취소 여부가 일치해야 합니다.");
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