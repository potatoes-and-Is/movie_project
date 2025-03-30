package org.movieproject.model;

import java.sql.Timestamp;

public class Tickets {
<<<<<<< HEAD

    private int ticketId; // 예약 ID
    private char cancelStatus; // 티켓 취소 여부
    private int userId; // 회원 ID (외래키)
    private int cinema_info_id; // 예약 ID
=======
    private int ticketId;
    private char cancelStatus;
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    private String userNickname;
    private String movieTitle;
    private Timestamp scheduleStartTime;
    private String seatNumber;
<<<<<<< HEAD

    public Tickets(int ticketId, char cancelStatus, int userId, int cinema_info_id) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.cinema_info_id = cinema_info_id;
    }

    public Tickets(int ticketId, char cancelStatus, int userId, int cinema_info_id, String userNickname, String movieTitle, Timestamp scheduleStartTime, String seatNumber) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.cinema_info_id = cinema_info_id;
        this.userNickname = userNickname;
        this.movieTitle = movieTitle;
        this.scheduleStartTime = scheduleStartTime;
        this.seatNumber = seatNumber;
    }
=======
//    private int paymentId;
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2

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

<<<<<<< HEAD
=======
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    public char getCancelStatus() {
        return cancelStatus;
    }

<<<<<<< HEAD
    public int getUserId() {
        return userId;
    }

    public int getCinema_info_id() {
        return cinema_info_id;
=======
    public void setCancelStatus(char cancelStatus) {
        this.cancelStatus = cancelStatus;
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    }

    public String getUserNickname() {
        return userNickname;
    }

<<<<<<< HEAD
=======
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

>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    public String getMovieTitle() {
        return movieTitle;
    }

<<<<<<< HEAD
=======
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    public String getScheduleStartTime() {
        return String.valueOf(scheduleStartTime);
    }

<<<<<<< HEAD
    public String getSeatNumber() {
        return seatNumber;
    }
}
=======
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
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
