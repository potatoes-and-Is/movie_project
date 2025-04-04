package org.movieproject.view;

import org.movieproject.model.Tickets;
import org.movieproject.model.Users;
import org.movieproject.service.TicketsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLXML;

public class TicketsView {

    private final TicketsService ticketsService;
    private Connection connection;

    public TicketsView(Connection connection) {
        this.ticketsService = new TicketsService(connection);
    }

    public Tickets saveTicket(int cinemaInfoId, int userId) throws SQLException {
        Tickets ticket = new Tickets(cinemaInfoId, userId);
        try{
            boolean success = ticketsService.saveTicket(ticket);
            if (success) {
                System.out.println("성공적으로 저장되었습니다.");
                return ticket;
            } else{
                System.out.println("저장에 실패했습니다.");
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
