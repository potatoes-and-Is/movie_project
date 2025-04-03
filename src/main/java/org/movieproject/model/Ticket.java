package org.movieproject.model;

import java.sql.Timestamp;

public class Ticket {

    private int ticketId;
    private String cancelStatus;
    private int cinemaInfoId; // Cinema_Infos 테이블 외래키
    private int userId; // Users 테이블 외래키


    public Ticket() {}

    public Ticket(int ticketId, String cancelStatus, int cinemaInfoId, int userId) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.cinemaInfoId = cinemaInfoId;
        this.userId = userId;
    }

    // Getters & Setters
    public int getTicketId() {
        return ticketId;
    }
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
    public String getCancelStatus() {
        return cancelStatus;
    }
    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }
    public int getCinemaInfoId() {
        return cinemaInfoId;
    }
    public void setCinemaInfoId(int cinemaInfoId) {
        this.cinemaInfoId = cinemaInfoId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "예약된 티켓 정보" +
                "티켓Id=" + ticketId +
                ", cinemaInfoId=" + cinemaInfoId +
                ", userId=" + userId +
                '}';
    }


}
