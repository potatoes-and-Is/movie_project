package org.movieproject.model;

public class Tickets {
    private int ticketId;
    private char cancelStatus;
    private int userId;
    private int paymentId;

    public Tickets(int ticketId, String userNickname, String movie_title, int schedule_start_time, int seat_number) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
