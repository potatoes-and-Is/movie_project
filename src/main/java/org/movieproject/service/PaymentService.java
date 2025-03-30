package org.movieproject.service;

import org.movieproject.dao.PaymentDao;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentService {

    private final PaymentDao paymentDao;

    public PaymentService(Connection connection) {
        this.paymentDao = new PaymentDao(connection);
    }

    // 결제 처리 후 생성된 payment_id 반환
    public int processPayment(String paymentMethod, int paymentPrice) {
        try {
            return paymentDao.insertPayment(paymentMethod, paymentPrice);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
