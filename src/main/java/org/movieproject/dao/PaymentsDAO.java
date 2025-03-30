package org.movieproject.dao;


import org.movieproject.model.Payments;
import org.movieproject.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentsDAO {

    private final Connection connection;

    public PaymentsDAO(Connection connection) {
        this.connection = connection;
    }

    /* 결제 ( insert)  */
    /* payment_method, payment_price(고정값) 전달 */
    public boolean payMovie(Payments payments) {
        String query = QueryUtil.getQuery("payMovie");

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, payments.getPaymentMethod());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
