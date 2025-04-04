package org.movieproject.dao;

import org.movieproject.model.Users;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
    private final Connection connection;

    public UsersDao(Connection connection) {
        this.connection = connection;
    }

/*  user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '회원 Id(PK)',
    user_nickname VARCHAR(30) NOT NULL COMMENT '닉네임',
    user_password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    user_status CHAR(1) DEFAULT 'Y' NOT NULL COMMENT '상태',
    user_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일'*/

    /**
     * 📌 모든 사용자 조회 (READ)
     * - XML에서 `getAllUsers` 쿼리를 가져와 실행
     */
    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllUsers"); // XML에서 쿼리 로드

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                users.add(new Users(
                        rs.getInt("user_id"),
                        rs.getString("user_nickname"),
                        rs.getString("user_password"),
                        rs.getString("user_status"),
                        rs.getTimestamp("user_created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 📌 단일 사용자 조회 (READ)
     */
    public Users getUserById(int userId) {
        String query = QueryUtil.getQuery("getUserById");
        Users users = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                users = new Users(
                        rs.getInt("user_id"),
                        rs.getString("user_nickname"),
                        rs.getString("user_password"),
                        rs.getString("user_status"),
                        rs.getTimestamp("user_created_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Users getUserByNickname(String userNickname) {
        String query = QueryUtil.getQuery("getUserByNickname");
        Users users = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userNickname);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                users = new Users(
                        rs.getInt("user_id"),
                        rs.getString("user_nickname"),
                        rs.getString("user_password"),
                        rs.getString("user_status"),
                        rs.getTimestamp("user_created_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 📌 사용자 추가 (CREATE)
    public boolean addUser(Users users) {
        String query = QueryUtil.getQuery("addUser");

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, users.getUserNickname());
            ps.setString(2, users.getUserPassword());
            ps.setString(3, users.getUserStatus());

            LocalDateTime now = LocalDateTime.now();
            ps.setTimestamp(4, Timestamp.valueOf(now));
            users.setUserCreatedAt(now); // 객체에도 시간

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Users login(String userNickname, String userPassword){
        String query = QueryUtil.getQuery("loginUser");
        Users users = null;
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, userNickname);
            ps.setString(2, userPassword);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                users = new Users(
                        rs.getInt("user_id"),
                        rs.getString("user_nickname"),
                        rs.getString("user_password"),
                        rs.getString("user_status"),
                        rs.getTimestamp("user_created_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * 📌 사용자 삭제 (DELETE)
     */
    public boolean deleteUser(int userId) {
        String query = QueryUtil.getQuery("deleteUser");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeStatusUser(String userNickname) {
        String query = QueryUtil.getQuery("changeStatusUser");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userNickname);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int changeUserPassword(String nickname, String newPassword) {
        String query = QueryUtil.getQuery("changeUserPassword");
        int result = 0;

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, newPassword);
            ps.setString(2, nickname);
            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }







}
