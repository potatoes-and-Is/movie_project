package org.movieproject.model;

import java.sql.Timestamp;

public class Tickets {

    private int ticketId; // 예약 ID
    private char cancelStatus; // 티켓 취소 여부
    private int userId; // 회원 ID (외래키)
    private int cinema_info_id; // 예약 ID
    private String userNickname;
    private String movieTitle;
    private String scheduleStartTime;
    private String seatNumber;

    public Tickets(int ticketId, char cancelStatus, int userId, int cinema_info_id) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.cinema_info_id = cinema_info_id;
    }

    public Tickets(int ticketId, char cancelStatus, int userId, int cinema_info_id, String userNickname, String movieTitle, String scheduleStartTime, String seatNumber) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.cinema_info_id = cinema_info_id;
        this.userNickname = userNickname;
        this.movieTitle = movieTitle;
        this.scheduleStartTime = scheduleStartTime;
        this.seatNumber = seatNumber;
    }

    public Tickets(int ticketId, String movieTitle, String scheduleStartTime) {
        this.ticketId = ticketId;
        this.movieTitle = movieTitle;
        this.scheduleStartTime = scheduleStartTime;
    }

    public Tickets(int ticketId, String userNickname, String movie_title, String schedule_start_time, String seat_number) {
        this.ticketId = ticketId;
        this.userNickname = userNickname;
        this.movieTitle = movie_title;
        this.scheduleStartTime = schedule_start_time;
        this.seatNumber = seat_number;
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

    public int getCinema_info_id() {
        return cinema_info_id;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}