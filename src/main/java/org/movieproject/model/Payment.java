package org.movieproject.model;

import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private int paymentPrice;
    private int ticketId; // Tickets 테이블과 외래키 관계

    public Payment() {}

    public Payment(int paymentId, String paymentMethod, LocalDateTime paymentTime, int paymentPrice, int ticketId) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.paymentPrice = paymentPrice;
        this.ticketId = ticketId;
    }

    public int getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }
    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
    public int getPaymentPrice() {
        return paymentPrice;
    }
    public void setPaymentPrice(int paymentPrice) {
        this.paymentPrice = paymentPrice;
    }
    public int getTicketId() {
        return ticketId;
    }
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentTime=" + paymentTime +
                ", paymentPrice=" + paymentPrice +
                ", ticketId=" + ticketId +
                '}';
    }

}
