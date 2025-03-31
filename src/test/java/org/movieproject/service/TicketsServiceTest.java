package org.movieproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.movieproject.config.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketsServiceTest {
    private Connection connection;
    private TicketsService ticketsService;

    private static final int TEST_TICKET_ID = 27;
    private static final String TEST_CANCEL_STATUS = "N";

    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            ticketsService = new TicketsService(connection);

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE tickets SET cancel_status = 'Y' WHERE ticket_id = ?");
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }
}