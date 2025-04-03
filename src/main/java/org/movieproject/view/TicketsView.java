package org.movieproject.view;

import org.movieproject.model.Tickets;
import org.movieproject.service.TicketsService;

import java.sql.Connection;
import java.sql.SQLException;

public class TicketsView {

    private final TicketsService ticketsService;
    private Connection connection;

    public TicketsView(Connection connection) {
        this.ticketsService = new TicketsService(connection);
    }

    public int saveTicket(int cinemaInfoId, int userId) throws SQLException {
        Tickets ticket = new Tickets(cinemaInfoId, userId);
        int ticketId = 0;
        try{
            ticketId = ticketsService.saveTicket(ticket);

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return ticketId;
    }
}
