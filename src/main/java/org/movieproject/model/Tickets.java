package org.movieproject.model;

public class Tickets {

    private int ticketId; // 예약 ID
    private char cancelStatus; // 티켓 취소 여부
    private int userId; // 회원 ID (외래키)
    private int paymentId; // 결제 ID (외래키)
    private int reservationId; // 예약 ID

    public Tickets(int ticketId, char cancelStatus, int userId, int paymentId, int reservationId) {
        this.ticketId = ticketId;
        this.cancelStatus = cancelStatus;
        this.userId = userId;
        this.paymentId = paymentId;
        this.reservationId = reservationId;
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

    public int getPaymentId() {
        return paymentId;
    }

    public int getReservationId() {
        return reservationId;
    }
}