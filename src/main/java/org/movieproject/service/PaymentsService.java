package org.movieproject.model;

import org.movieproject.dao.PaymentsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class PaymentsService {
    private static final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    private final PaymentsDao paymentsDao;
    private final Connection connection;

    public PaymentsService(Connection connection) {
        this.connection = connection;
        this.paymentsDao = new PaymentsDao(connection);
    }

    public boolean payMovie(Payments payments) {
        return paymentsDao.payMovie(payments);
    }

}
