package org.movieproject.model;

public class PayMethod {

    private int payMethodId;
    private String payMethodNumber;
    private int payMethodBalance;
    private int userId;
    private int payMethodPwd;

    public PayMethod(int payMethodId, String payMethodNumber, int payMethodBalance, int userId, int payMethodPwd) {
        this.payMethodId = payMethodId;
        this.payMethodNumber = payMethodNumber;
        this.payMethodBalance = payMethodBalance;
        this.userId = userId;
        this.payMethodPwd = payMethodPwd;
    }

    public int getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(int payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getPayMethodNumber() {
        return payMethodNumber;
    }

    public void setPayMethodNumber(String payMethodNumber) {
        this.payMethodNumber = payMethodNumber;
    }

    public int getPayMethodBalance() {
        return payMethodBalance;
    }

    public void setPayMethodBalance(int payMethodBalance) {
        this.payMethodBalance = payMethodBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPayMethodPwd() {
        return payMethodPwd;
    }

    public void setPayMethodPwd(int payMethodPwd) {
        this.payMethodPwd = payMethodPwd;
    }

    @Override
    public String toString() {
        return "PayMethod{" +
                "payMethodId=" + payMethodId +
                ", payMethodNumber='" + payMethodNumber + '\'' +
                ", payMethodBalance=" + payMethodBalance +
                ", userId=" + userId +
                ", payMethodPwd=" + payMethodPwd +
                '}';
    }
}
