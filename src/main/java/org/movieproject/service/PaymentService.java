package org.movieproject.service;

import org.movieproject.dao.PaymentDao;
import org.movieproject.model.PayMethod;
import org.movieproject.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentDao paymentDao;
    private final Connection connection;

    public PaymentService(Connection connection) {
        this.connection = connection;
        this.paymentDao = new PaymentDao(connection);
    }

    public List<PayMethod> getPaymentStatus(List<Payment> payMethods) {
        List<PayMethod> payMethods = paymentDao.getAllPayMethods(payMethods);

        if (payMethods == null) {
            log.error("조회한 결제 수단의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            return null;
        }

        return payMethods;
    }

//    public boolean payMovie(Payment payments) {
//        return paymentsDao.payMovie(payments);
//    }

}
