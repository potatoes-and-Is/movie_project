package org.movieproject.service;

import org.movieproject.dao.PaymentDao;
import org.movieproject.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class PaymentsService {
    private static final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    private final PaymentDao paymentsDao;
    private final Connection connection;

    public PaymentsService(Connection connection) {
        this.connection = connection;
        this.paymentsDao = new PaymentDao(connection);
    }

//    public boolean payMovie(Payment payments) {
//        return paymentsDao.payMovie(payments);
//    }

}
