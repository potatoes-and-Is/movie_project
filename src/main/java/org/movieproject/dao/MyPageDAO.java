package org.movieproject.dao;

import org.movieproject.model.Tickets;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyPageDAO {

    private final Connection connection;

    public MyPageDAO(Connection connection) {
        this.connection = connection;
    }


    /* 닉네임, 비밀번호 값 저장 */
//    public List<Users> getFindUser() {
//        List<Users> users = new ArrayList<>();
//        String query = QueryUtil.getQuery("getFindUser"); // XML에서 쿼리 로드
//        try (Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                users.add(new Users(
//                        rs.getString("user_nickname"),
//                        rs.getString("user_password")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
    /* 닉네임, 비밀번호 값 저장 */
    public int validateLogin(String nickname, String password) {
        String sql = "SELECT user_id FROM Users WHERE user_nickname = ? AND user_password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nickname);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 로그인 실패
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
                        rs.getInt("ticket_id"), // --> tickets 배열의 인덱스 불러오기 변경
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