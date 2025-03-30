package org.movieproject.service;

<<<<<<< HEAD
import org.movieproject.dao.PaymentsDAO;
=======
import org.movieproject.dao.PaymentsDao;
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
import org.movieproject.model.Payments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class PaymentsService {
    private static final Logger log = LoggerFactory.getLogger(PaymentsService.class);
<<<<<<< HEAD
    private final PaymentsDAO paymentsDao;
=======
    private final PaymentsDao paymentsDao;
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    private final Connection connection;

    public PaymentsService(Connection connection) {
        this.connection = connection;
<<<<<<< HEAD
        this.paymentsDao = new PaymentsDAO(connection);
=======
        this.paymentsDao = new PaymentsDao(connection);
>>>>>>> f870e23cb09f1082e0032712db7df97b14d922a2
    }

    public boolean payMovie(Payments payments) {
        return paymentsDao.payMovie(payments);
    }

}
