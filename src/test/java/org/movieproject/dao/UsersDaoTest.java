package org.movieproject.dao;

import org.junit.jupiter.api.*;
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
    @DisplayName("모든 사용자 조회 테스트")
    void testGetAllUsers() {
        // Act: DAO를 통해 데이터 조회
        List<Users> users = usersDao.getAllUsers();

        // Assert: 결과 검증
        Assertions.assertNotNull(users, "사용자 목록은 null이 아니어야 합니다.");
        Assertions.assertFalse(users.isEmpty(), "사용자 목록은 비어 있지 않아야 합니다.");
    }

    @Test
    @DisplayName("특정 사용자 조회 테스트")
    void testGetUserById() {
        // Act: 특정 사용자 조회
        Users retrievedUser = usersDao.getUserById(testUserId);

        // Assert: 조회된 값 검증
        Assertions.assertNotNull(retrievedUser, "사용자가 존재해야 합니다.");
        Assertions.assertEquals(TEST_USERNICKNAME, retrievedUser.getUserNickname(), "사용자 이름이 일치해야 합니다.");
        Assertions.assertEquals(TEST_PASSWORD, retrievedUser.getUserPassword(), "이메일이 일치해야 합니다.");
    }

    @Test
    void getUserByNickname() {
// Act: 특정 사용자 조회
        Users retrievedUser = usersDao.getUserByNickname(TEST_USERNICKNAME);

        // Assert: 조회된 값 검증
        Assertions.assertNotNull(retrievedUser, "사용자가 존재해야 합니다.");
        Assertions.assertEquals(TEST_USERNICKNAME, retrievedUser.getUserNickname(), "사용자 이름이 일치해야 합니다.");
        Assertions.assertEquals(TEST_PASSWORD, retrievedUser.getUserPassword(), "이메일이 일치해야 합니다.");
    }

    @Test
    @DisplayName("사용자 추가 테스트")
    void testAddUser() {
        // Arrange: 새로운 사용자 객체 생성
        Users newUser = new Users(0, "new_user", "newpassword", "Y", null);

        // Act: 사용자 추가
        boolean isAdded = usersDao.addUser(newUser);

        // Assert: 추가 확인
        Assertions.assertTrue(isAdded, "사용자가 성공적으로 추가되어야 합니다.");
    }

    @Test
    void login() {
        Users user = usersDao.login(TEST_USERNICKNAME, TEST_PASSWORD);
        Assertions.assertNotNull(user, "로그인에 성공해야 합니다.");
        Assertions.assertEquals(TEST_USERNICKNAME, user.getUserNickname(), "닉네임이 일치해야 합니다.");

    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void testDeleteUser() {
        // Act: 사용자 삭제
        boolean isDeleted = usersDao.deleteUser(testUserId);

        // Assert: 삭제 결과 확인
        Assertions.assertTrue(isDeleted, "사용자가 성공적으로 삭제되어야 합니다.");

        // 삭제 후 다시 조회하면 null이 되어야 함
        Users retrievedUser = usersDao.getUserById(testUserId);
        Assertions.assertNull(retrievedUser, "삭제된 사용자는 조회되지 않아야 합니다.");
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