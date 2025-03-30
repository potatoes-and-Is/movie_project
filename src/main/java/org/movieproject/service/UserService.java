package org.movieproject.service;

import org.movieproject.dao.UserDao;
import org.movieproject.model.User;

import java.sql.Connection;

public class UserService {
    private final UserDao userDao;

    public UserService(Connection connection) {
        this.userDao = new UserDao(connection);
    }

    public boolean registerUser(User user) {
        try {
            return userDao.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String nickname, String password) {
        try {
            return userDao.login(nickname, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
