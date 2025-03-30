package org.movieproject.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private int userId;
    private String nickname;
    private String password;
    private String status;
    private LocalDateTime userCreatedAt;

    public User() {}

    public User(String nickname, String password, String status) {
        this.nickname = nickname;
        this.password = password;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(LocalDateTime userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(nickname, user.nickname) && Objects.equals(password, user.password) && Objects.equals(status, user.status) && Objects.equals(userCreatedAt, user.userCreatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, nickname, password, status, userCreatedAt);
    }
}
