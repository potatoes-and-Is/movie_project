package org.movieproject.model;

import java.sql.Timestamp;

public class Tickets {

    private int ticketId; // 예약 ID
    private char cancelStatus; // 티켓 취소 여부
    private int userId; // 회원 ID (외래키)
    private int cinemaInfoId; // 예약 ID
    private String userNickname;
    private String movieTitle;
    private Timestamp scheduleStartTime;
    private String seatNumber;

    public Tickets(int ticketId, char cancelStatus, int userId, int cinemaInfoId) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.cinemaInfoId = cinemaInfoId;
    }

    public Tickets(int ticketId, char cancelStatus, int userId, int cinemaInfoId, String userNickname, String movieTitle, Timestamp scheduleStartTime, String seatNumber) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.cinemaInfoId = cinemaInfoId;
        this.userNickname = userNickname;
        this.movieTitle = movieTitle;
        this.scheduleStartTime = scheduleStartTime;
        this.seatNumber = seatNumber;
    }

    public Tickets(int ticketId, String movieTitle, Timestamp scheduleStartTime) {
        this.ticketId = ticketId;
        this.movieTitle = movieTitle;
        this.scheduleStartTime = scheduleStartTime;
    }

    public Tickets(int ticketId, String userNickname, String movie_title, Timestamp schedule_start_time, String seat_number) {
        this.ticketId = ticketId;
        this.userNickname = userNickname;
        this.movieTitle = movie_title;
        this.scheduleStartTime = schedule_start_time;
        this.seatNumber = seat_number;
    }

    public Tickets(int cinemaInfoId, int userId) {
        this.cinemaInfoId = cinemaInfoId;
        this.userId = userId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public char getCancelStatus() {
        return cancelStatus;
    }

    public int getUserId() {
        return userId;
    }

    public int getcinemaInfoId() {
        return cinemaInfoId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getScheduleStartTime() {
        return String.valueOf(scheduleStartTime);
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}