package org.movieproject.dao;

import org.movieproject.model.Ticket;
import org.movieproject.model.TicketInfo;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {

    private final Connection connection;

    public TicketDao(Connection connection) {
        this.connection = connection;
    }

    // Tickets 테이블에 티켓을 생성하고, 생성된 ticket_id 반환
    public int createTicket(String cancelStatus, int cinemaInfoId, int userId, int paymentId) throws SQLException {
        String sql = QueryUtil.getQuery("insertTicket");
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cancelStatus);
            pstmt.setInt(2, cinemaInfoId);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, paymentId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("티켓 생성에 실패하였습니다.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // 생성된 티켓 ID 반환
                } else {
                    throw new SQLException("티켓 생성 후 ID를 가져올 수 없습니다.");
                }
            }
        }
    }

    public Ticket getTicketById(int ticketId) throws SQLException {
        String sql = QueryUtil.getQuery("getTicketById");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setCancelStatus(rs.getString("cancel_status"));
                    ticket.setCinemaInfoId(rs.getInt("cinema_info_id"));
                    ticket.setUserId(rs.getInt("user_id"));
                    return ticket;
                }
            }
        }
        return null;
    }

    // 사용자 ID로 예매내역 조회 (JOIN 쿼리 사용)
    public List<TicketInfo> getTicketInfoByUser(int userId) throws SQLException {
        List<TicketInfo> list = new ArrayList<>();
        String sql = QueryUtil.getQuery("getTicketInfoByUser");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TicketInfo info = new TicketInfo(
                            rs.getInt("ticket_id"),
                            rs.getString("movie_title"),
                            rs.getString("schedule_start_time")
                    );
                    list.add(info);
                }
            }
        }
        return list;
    }
}
