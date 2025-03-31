package org.movieproject.service;

import org.movieproject.dao.UserDao;
import org.movieproject.model.User;

import java.sql.Connection;
import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(Connection connection) {
        this.userDao = new UserDao(connection);
    }

    // 회원 등록
    public boolean registerUser(User newUser) {
        try {
            List<User> userList = userDao.checkUserNickname();
            for (User user : userList) {
                if (newUser.getNickname().equals(user.getNickname())) {
                    return false;
                }
            }
            return userDao.createUser(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인 서비스
    public User login(String nickname, String password) {
        try {
            return userDao.login(nickname, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
