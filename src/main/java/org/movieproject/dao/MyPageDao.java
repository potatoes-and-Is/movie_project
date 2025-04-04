package org.movieproject.dao;

import org.movieproject.model.Tickets;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyPageDao {

    private final Connection connection;

    public MyPageDao(Connection connection) {
        this.connection = connection;
    }

     /* 회원별 티켓 정보 간단히 출력 */
    public List<Tickets> getTicketsByUserId(int userId) {
        List<Tickets> tickets = new ArrayList<>();

        // getTicketsByUserId 쿼리 로드
        String query = QueryUtil.getQuery("getTicketsByUserId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // userId로 직접 조회
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Tickets ticket = new Tickets(
                        rs.getInt("ticket_id"),
                        rs.getString("movie_title"),
                        rs.getString("schedule_start_time")
                );
                tickets.add(ticket);  // 결과를 리스트에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    /* 티켓 상세 정보 출력 */
    public Tickets getTicketById(int ticketId) {
        Tickets ticketInfo = null; // 반환할 티켓 객체

        // getTicketById 쿼리 로드
        String query = QueryUtil.getQuery("getTicketById");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // ticketID로 직접 조회
            ps.setInt(1, ticketId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 결과가 있으면 해당 티켓 정보 반환
                    ticketInfo = new Tickets(
                            rs.getInt("ticket_id"),
                            rs.getString("user_nickname"),
                            rs.getString("movie_title"),
                            rs.getString("schedule_start_time"),
                            rs.getString("seat_number")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketInfo; // 찾은 티켓을 반환
    }
}