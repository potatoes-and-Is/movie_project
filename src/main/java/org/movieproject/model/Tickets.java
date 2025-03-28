package org.movieproject.model;

public class Tickets {

    private int ticketId; // 예약 ID
    private char cancelStatus; // 티켓 취소 여부
    private int userId; // 회원 ID (외래키)
    private int cinema_info_id; // 예약 ID

    public Tickets(int ticketId, char cancelStatus, int userId, int reservationId) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.cinema_info_id = reservationId;
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
}