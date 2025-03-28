package org.movieproject.model;

import java.time.LocalDateTime;

public class Payments {
    private int paymentId;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private int payment_price;

    public Payments(int paymentId, String paymentMethod, LocalDateTime paymentTime, int payment_price) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.payment_price = payment_price;
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

    public int getPayment_price() {
        return payment_price;
    }

    public void setPayment_price(int payment_price) {
        this.payment_price = payment_price;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentTime=" + paymentTime +
                ", payment_price=" + payment_price +
                '}';
    }
}
