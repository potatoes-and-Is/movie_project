package org.movieproject.model;

import java.time.LocalDateTime;

public class Users {
<<<<<<< HEAD
<<<<<<< HEAD
    /*
      userId - int, 회원ID(PK)
      userNickname - String, 회원 닉네임
      userPassword - String, 비밀번호
      userStatus - String, 상태
      userCreatedAt - localdatetime, 등록일
      * */
=======
>>>>>>> f4f71e99c44b1d1eca80a9f22f32ec61ab10210d
=======
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    public Users(String userNickname, String userPassword) {
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }

<<<<<<< HEAD
>>>>>>> f4f71e99c44b1d1eca80a9f22f32ec61ab10210d
=======
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2

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
<<<<<<< HEAD
<<<<<<< HEAD

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
=======
>>>>>>> f4f71e99c44b1d1eca80a9f22f32ec61ab10210d
=======
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
}
