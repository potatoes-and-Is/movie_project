package org.movieproject.model;

import java.sql.Timestamp;

public class Tickets {
    private int ticketId;
    private char cancelStatus;
    private String userNickname;
    private String movieTitle;
    private Timestamp scheduleStartTime;
    private String seatNumber;
//    private int paymentId;

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

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public char getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(char cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    //    public int getPaymentId() {
//        return paymentId;
//    }
//
//    public void setPaymentId(int paymentId) {
//        this.paymentId = paymentId;
//    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getScheduleStartTime() {
        return String.valueOf(scheduleStartTime);
    }

    public void setScheduleStartTime(Timestamp scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
