package org.movieproject.dao;

import org.movieproject.model.Tickets;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketsDao {

    private final Connection connection;

    public TicketsDao(Connection connection) {
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
                        rs.getInt("cinema_info_id")
                );
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

    /* 생성된 티켓 저장 */
    public int saveTicket(Tickets tickets) {
        String query = QueryUtil.getQuery("saveTicket");
        int ticketId = 0;

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, tickets.getcinemaInfoId());
            ps.setInt(2, tickets.getUserId());

            int res = ps.executeUpdate();
            if (res > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    ticketId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketId;
    }
}