package org.movieproject.dao;

import org.movieproject.model.User;
import org.movieproject.util.QueryUtil;

import java.sql.*;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createUser(User user) throws SQLException {
        String sql = QueryUtil.getQuery("insertUser");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getStatus());
            if(pstmt.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }

    public User login(String nickname, String password) throws SQLException {
        String sql = QueryUtil.getQuery("loginUser");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nickname);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_nickname"),
                            rs.getString("user_password"),
                            rs.getString("user_status")
                    );
                }
            }
        }
        return null; // 로그인 실패 시 null 반환
    }
}
