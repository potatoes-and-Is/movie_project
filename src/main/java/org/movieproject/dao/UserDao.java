package org.movieproject.dao;

import org.movieproject.model.User;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // 회원 생성
    public boolean createUser(User user) throws SQLException {
        String sql = QueryUtil.getQuery("insertUser");
        //
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getStatus());
            return pstmt.executeUpdate() > 0;
        }
    }

    // 회원가입 시 아이디(닉네임) 확인
    public List<User> checkUserNickname() throws SQLException {
        String sql = QueryUtil.getQuery("getUserNickname");
        List<User> users = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setNickname(rs.getString("user_nickname"));
                users.add(user);
            }
        }
        return users;
    }

    // 로그인
    public User login(String nickname, String password) throws SQLException {
        String sql = QueryUtil.getQuery("loginUser");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nickname);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"), // id 값도 전달하여 사용할 수 있도록 한다.
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
