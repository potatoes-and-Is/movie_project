package org.movieproject.service;

import org.movieproject.dao.TicketsDAO;
import org.movieproject.model.Tickets;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TicketsService {

    //    private static final Logger logger = Logger.getLogger(TicketsService.class);
    private final TicketsDAO ticketsDAO;
    private final Connection connection;

    public TicketsService(Connection connection) {
        this.connection = connection;
        ticketsDAO = new TicketsDAO(connection);
    }

    // 티켓이 존재하는지 확인
    public Tickets getTicketsById(int ticketsId) throws SQLException {
        Tickets tickets = ticketsDAO.getTicketsById(ticketsId);

        if (tickets == null) {
            throw new SQLException("예매 정보가 존재하지 않습니다.");
        }
        return tickets;
    }

    public List<Tickets> getTicketsByUserId(int userId) throws SQLException {
        List<Tickets> tickets = ticketsDAO.getTicketsByUserId(userId);
        if (tickets.isEmpty()) {
//            System.out.println("예매 정보가 없습니다.");
            return Collections.emptyList();
        }
        return tickets;
    }

    public boolean cancelTicket(int ticketId) throws SQLException {
        // 티켓이 존재하는지 확인
        Tickets existingTickets = getTicketsById(ticketId);
        if (existingTickets == null) {
            throw new IllegalArgumentException("예매 건을 찾을 수 없습니다.");
        }
        boolean result = ticketsDAO.cancelTicket(existingTickets);

        // 예매 취소 여부 'Y'로 변경 시 오류 발생할 경우
        if (!result) {
            throw new SQLException("예매 취소하는 과정에서 오류가 발생하였습니다.");
        }
        return true;
    }
}