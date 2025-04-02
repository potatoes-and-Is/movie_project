package org.movieproject.model;

import java.time.LocalDateTime;

public class Users {
    private int userId;
    private String userNickname;
    private String userPassword;
    private String userStatus;
    private LocalDateTime userCreatedAt;

    public Users(int userId, String userNickname, String userPassword, String userStatus, LocalDateTime userCreatedAt) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.userCreatedAt = userCreatedAt;
    }
    public Users(String userNickname, String userPassword) {
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public LocalDateTime getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(LocalDateTime userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    @Override
    public String toString() {
        return "회원정보{" +
                "회원번호 ID = " + userId +
                ", 닉네임 = '" + userNickname + '\'' +
                ", 비밀번호 = '" + userPassword + '\'' +
                ", 활성상태 = '" + userStatus + '\'' +
                ", 등록일 = " + userCreatedAt +
                '}';
    }
}
