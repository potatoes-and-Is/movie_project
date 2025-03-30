package org.movieproject.service;

import org.movieproject.dao.MyPageDAO;
import org.movieproject.model.Tickets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MyPageService {

    private static final Logger log = LoggerFactory.getLogger(MyPageService.class);

    private final MyPageDAO myPageDao;
    private final Connection connection;

    /* 생성자 주입 (의존성 주입) */
    public MyPageService(Connection connection) {
        this.myPageDao = new MyPageDAO(connection);
        this.connection = connection;
    }

    // 회원별 티켓 아이디 조회
//    public void retrieveTickets(int userId) {
//        String sql = "SELECT tickets_id FROM Tickets WHERE user_id = ?";
//
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setInt(1, userId);
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    System.out.println("보유한 티켓 ID: " + rs.getInt("tickets_id"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    /* 테스트용 로그인 */
    public int validateLogin(String nickname, String password) {
        return myPageDao.validateLogin(nickname, password);
    }

    /* 회원별 티켓 정보 간단히 출력 */
    public List<Tickets> getTicketsByUserId(int userId) throws SQLException {
        return myPageDao.getTicketsByUserId(userId);
    }

    /* 티켓 상세 정보 출력 */
    public Tickets getTicketById(int ticketId) throws SQLException {
        // MyPageDao 인스턴스를 통해 메서드 호출
        Tickets ticketInfo = myPageDao.getTicketById(ticketId);

        if (ticketInfo == null) {
            log.error("티켓 ID가 존재하지 않습니다.");
            return null;
        }
        return ticketInfo;
    }
}
