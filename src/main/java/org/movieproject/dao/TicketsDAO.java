package org.movieproject.dao;

import org.movieproject.model.Tickets;
import org.movieproject.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketsDAO {

    private final Connection connection;

    public TicketsDAO(Connection connection) {
        this.connection = connection;
    }

    // 티켓 ID별 티켓 정보 조회
    public Tickets getTicketsById(int ticketId) {
        String query = QueryUtil.getQuery("getTicketsById");
        Tickets tickets = null;


        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ticketId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tickets = new Tickets(
                        rs.getInt("ticket_id"),
                        rs.getString("cancel_status").charAt(0),
                        rs.getInt("user_id"),
                        rs.getInt("payment_id"),
                        rs.getInt("reservation_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // 회원 ID별 티켓 리스트 조회
    public List<Tickets> getTicketsByUserId(int userId) {
        String query = QueryUtil.getQuery("getTicketsByUserId");
        List<Tickets> tickets = new ArrayList<Tickets>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tickets.add(new Tickets(
                        rs.getInt("ticket_id"),
                        rs.getString("cancel_status").charAt(0),
                        rs.getInt("user_id"),
                        rs.getInt("payment_id"),
                        rs.getInt("reservation_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // 예매 취소 시 예매 취소 여부 'Y'로 변경
    public boolean cancelTicket(Tickets tickets) {
        String query = QueryUtil.getQuery("cancelTicket");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, tickets.getTicketId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
