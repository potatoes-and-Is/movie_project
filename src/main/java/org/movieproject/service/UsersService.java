package org.movieproject.service;

import org.movieproject.dao.UsersDao;
import org.movieproject.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersService {
    private static final Logger log = LoggerFactory.getLogger(UsersService.class);
    private final UsersDao usersDao;
    private final Connection connection;

    /**
     * 📌 생성자 주입 (의존성 주입)
     */
    public UsersService(Connection connection) {
        this.connection = connection;
        this.usersDao = new UsersDao(connection);
    }

    /**
     * 📌 모든 사용자 조회
     * - 데이터 검증 후 반환
     */
    public List<Users> getAllUsers() throws SQLException {
        List<Users> users = usersDao.getAllUsers();

        if(users == null) {
            log.error("조회한 사용자의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            return null;
        }

        return usersDao.getAllUsers();
    }

    /**
     * 📌 단일 사용자 조회
     *
     * 이 메서드는 주어진 사용자 ID를 기반으로 데이터베이스에서 사용자를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자(ID)를 전달 받는 매개변수
     * @return 조회된 사용자 객체를 반환합니다. 사용자가 존재하지 않을 경우, 예외가 발생합니다.
     * @throws IllegalArgumentException 해당 ID의 사용자가 존재하지 않을 경우 발생합니다.
     * @throws SQLException 데이터베이스 접근 중 오류가 발생할 경우 발생합니다.
     */
    public Users getUserById(int userId) throws SQLException {
        Users users = usersDao.getUserById(userId);

        if (users == null) {
            throw new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다.");
        }
        return users;
    }

    public Users getUserByNickname(String userNickname){
        Users users = null;
        try {
            users = usersDao.getUserByNickname(userNickname);

            if (users == null) {
                throw new IllegalArgumentException("해당 닉네임의 사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("사용자 정보를 불러오는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 📌 사용자 등록 (CREATE)
     * - 이메일 중복 체크 후 추가
     * @param users 사용자 객체를 전달받음
     * @return boolean 성공여부를 boolean 타입으로 반환
     * @throws SQLException 데이터베이스 접근 중 오류가 발생할 경우 발생합니다.
     * @throws IllegalArgumentException 중복하는 닉네임이 존재하는 경우 발생
     */
    public boolean registerUser(Users users) throws SQLException {
        // 중복 닉네임 검사
        List<Users> existingUsers = getAllUsers();
        for (Users u : existingUsers) {
            if (u.getUserNickname().equals(users.getUserNickname())) {
                throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
            }
        }
        return usersDao.addUser(users);
    }

    // 사용자 로그인
    public Users login(String userNickname, String userPassword) {
        Users user = usersDao.getUserByNickname(userNickname); // 닉네임만으로 먼저 조회

        if (user == null) {
            System.out.println("존재하지 않는 닉네임입니다.");
            return null;
        }

        if (!user.getUserPassword().equals(userPassword)) {
            System.out.println("로그인 실패: 비밀번호가 올바르지 않습니다.");
            return null;
        }

        if ("N".equalsIgnoreCase(user.getUserStatus())) {
            System.out.println("비활성화된 계정입니다. 관리자에게 문의하세요.");
            return null;
        }

        return user; // 로그인 성공
    }
    /**
     * 📌 사용자 삭제 (DELETE)
     * - 삭제 시 확인 메시지 출력 후 진행
     *
     * @param userId 삭제할 사용자의 고유 ID
     * @return boolean 삭제 성공 여부를 반환합니다.
     * @throws IllegalArgumentException 삭제할 사용자가 존재하지 않는 경우 발생합니다.
     * @throws SQLException 데이터베이스 접근 중 오류가 발생할 경우 발생합니다.
     */
    public boolean deleteUser(int userId) throws SQLException {
        Users existingUser = getUserById(userId);
        if (existingUser == null) {
            throw new IllegalArgumentException("삭제할 사용자(회원 번호 ID)를 찾을 수 없습니다.");
        }
        return usersDao.deleteUser(userId);
    }

    public boolean changeStatusUser(String userNickname) throws SQLException {
        Users existingUser = getUserByNickname(userNickname);
        if (existingUser == null) {
            throw new IllegalArgumentException("삭제할 사용자(닉네임)를 찾을 수 없습니다.");
        }
        return usersDao.changeStatusUser(userNickname);
    }

    public boolean changeUserPassword(String userNickname, String newPassword) throws SQLException {
        Users existingUser = getUserByNickname(userNickname);
        if (existingUser == null) {
            throw new IllegalArgumentException("사용자(닉네임)를 찾을 수 없습니다.");
        }
        return usersDao.changeUserPassword(userNickname, newPassword) > 0;
    }



}
