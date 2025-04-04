package org.movieproject.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersDaoTest {
    private Connection connection;
    private UsersDao usersDao;
    private static final String TEST_USERNICKNAME = "test";
    private static final String TEST_PASSWORD = "test";
    private int testUserId;

    @BeforeEach
    void setUp() {
            try {
                connection = JDBCConnection.getConnection();
                connection.setAutoCommit(false); // 테스트 후 롤백을 위해 AutoCommit 비활성화
                usersDao = new UsersDao(connection);

                // Arrange: 테스트 데이터 삽입
                Users testUser = new Users(0, TEST_USERNICKNAME, TEST_PASSWORD, "Y", null);
                usersDao.addUser(testUser);

                // 삽입된 데이터 확인 (테스트용 ID 저장)
                List<Users> users = usersDao.getAllUsers();
                // 마지막 사용자를 조회함.
                testUserId = users.get(users.size() - 1).getUserId();

            } catch (SQLException e) {
                throw new RuntimeException("테스트 데이터 준비 중 오류 발생: " + e.getMessage());
            }
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void addUser() {
    }

    @Test
    void login() {
    }

    @Test
    void deleteUser() {
    }

    @AfterEach
    void tearDown() {
        try {
            connection.rollback(); // 테스트 데이터 롤백
            connection.setAutoCommit(true);
            connection.close();
            System.out.println("테스트 완료: 데이터 롤백 성공");
        } catch (SQLException e) {
            System.err.println("테스트 종료 중 오류 발생: " + e.getMessage());
        }
    }
}