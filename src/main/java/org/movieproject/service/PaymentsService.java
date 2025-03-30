package org.movieproject.service;

import org.movieproject.dao.PaymentsDAO;
import org.movieproject.model.Payments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class PaymentsService {
    private static final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    private final PaymentsDAO paymentsDao;
    private final Connection connection;

    public PaymentsService(Connection connection) {
        this.connection = connection;
        this.paymentsDao = new PaymentsDAO(connection);
    }

    public boolean payMovie(Payments payments) {
        return paymentsDao.payMovie(payments);
    }

}
