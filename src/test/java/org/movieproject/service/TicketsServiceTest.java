package org.movieproject.service;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketsServiceTest {
    private Connection connection;
    private TicketsService ticketsService;

    private static final String TEST_USERNAME = "test100";
    private static final String TEST_PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            ticketsService = new TicketsService(connection);

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Users (userNickname, userPassword) VALUES (?, ?)"
            );
            ps.setString(1, TEST_USERNAME);
            ps.setString(2, TEST_PASSWORD);
            ps.executeUpdate();
        } catch (SQLException e) {
            Assertions.fail("DB 연결 실패 : " + e.getMessage());
        }
    }

    @Test
    @DisplayName("티켓 ID별 티켓 조회 테스트 (정상 케이스)")
    void testGetTicket() {

    }
}