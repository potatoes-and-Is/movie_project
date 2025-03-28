package org.movieproject.dao;

//* 순서 . 로그인 -> 1. 예매정보 확인하기 클릭하면 -> 회원들의 예매정보에 대한 티켓정보리스트 간단히 보여줌 ->
//        * 한 건에 대한 전체 정보창 여기서 취소하시겠습니까 정하기
//        * y면 삭제 n이면 예매정보 확인하기 창으로
// 사용자 예매 정보 확인(Mypage)
// 결제 끝나고 예매내역 출력하면
//
// 1. 로그인 2. 예매정보 확인 창으로 이동
//
// 2 클릭 -> 티켓id, 예약id, 회원id(fk), 좌석(id), 상영정보 id(fk) 보여야 됨
// 취소하시겠습니까??(예약취소여부(status)) y면 예매취소 화면, n이면 로그인창?으로 이동


import org.movieproject.model.Tickets;
import org.movieproject.model.Users;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyPageDao {

    private final Connection connection;

    public MyPageDao(Connection connection) {
        this.connection = connection;
    }

    /*
    * 닉네임, 비밀번호 값 저장
     */
    public List<Users> getFindUser() {
        List<Users> users = new ArrayList<>();
        String query = QueryUtil.getQuery("getFindUser"); // XML에서 쿼리 로드
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
g
            while (rs.next()) {
                users.add(new Users(
                        rs.getString("user_nickname"),
                        rs.getString("user_password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

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

    // 회원의 예매 정보 조회
    // 회원id를 입력했을 때 나오는 티켓들 목록
    /**
     * 순서 . 로그인 -> 1. 예매정보 확인하기 클릭하면 -> 회원들의 예매정보(getTicketsByUserId)에
     * 대한 티켓정보리스트(티켓 id, 영화제목, 시간)간단히 보여줌
     * -> 티켓id(getTicketById,1번) 입력하면 티켓id 한 건에 대한 전체 정보창 보여줌. 이 창에서 여기서 취소하시겠습니까? 정하기
     * y면 삭제 n이면 예매정보 확인하기 창으로
     */
    public List<Tickets> getTicketsByUserId(Users user) {
        List<Tickets> tickets = new ArrayList<>();
        String query = QueryUtil.getQuery("getTicketsByUserId");

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery(query)) {

            while (rs.next()) {
                tickets.add(new Tickets(

                ))
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

     /** 특정 예매 정보 조회 (READ)
     * 순서 . 로그인 -> 1. 예매정보 확인하기 클릭하면 -> 회원들의 예매정보에 대한 티켓정보리스트 간단히 보여줌 ->
     * 티켓 아이디(1번) 클릭하면 한 건에 대한 전체 정보창 보여줌 이 창에서 여기서 취소하시겠습니까 정하기 getTicketById
     * y면 삭제 n이면 예매정보 확인하기 창으로
     */
    public Tickets getTicketById(int ticketId) {
        String query = QueryUtil.getQuery("getTicketById");

        Tickets ticket = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ticketId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ticket = new Tickets(
                        rs.getInt("ticket_id"),
                        rs.getString("user_nickname"),
                        rs.getString("movie_title"),
                        rs.getInt("schedule_start_time"),
                        rs.getInt("seat_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }
}

