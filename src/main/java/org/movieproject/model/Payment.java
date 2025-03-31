package org.movieproject.model;

import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private int paymentPrice;
    private LocalDateTime paymentTime;
    private int ticketId;

    public Payment(int paymentId, int paymentPrice, LocalDateTime paymentTime, int ticketId) {
        this.paymentId = paymentId;
        this.paymentPrice = paymentPrice;
        this.paymentTime = paymentTime;
        this.ticketId = ticketId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(int paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "paymentId=" + paymentId +
                ", paymentPrice=" + paymentPrice +
                ", paymentTime=" + paymentTime +
                ", ticketId=" + ticketId +
                '}';
    }
}
