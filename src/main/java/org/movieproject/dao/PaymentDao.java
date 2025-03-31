package org.movieproject.dao;


import org.movieproject.model.PayMethod;
import org.movieproject.model.Payment;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {

    private final Connection connection;

    public PaymentDao(Connection connection) {
        this.connection = connection;
    }

    /* 결제수단 목록 조회하기 */
    public List<PayMethod> getAllPayMethods (int userId) {
        List<PayMethod> payMethods = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllPayMethods");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
             ps.setInt(1, userId);

             try (ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     payMethods.add(new PayMethod(
                             rs.getInt("pay_method_id"),
                             rs.getString("pay_method_number"),
                             rs.getInt("pay_method_balance"),
                             rs.getInt("user_id"),
                             rs.getInt("pay_method_pwd")
                     ));
                 }
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payMethods;
    }

    /* 결제 수단 등록하기 */
    public boolean addPayMethod (PayMethod payMethod) {
        String query = QueryUtil.getQuery("addPayMethod");

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, payMethod.getPayMethodNumber());
            ps.setInt(2, payMethod.getUserId());
            ps.setInt(3, payMethod.getPayMethodPwd());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /* 결제 ( insert)  */
    /* payment_method, payment_price(고정값) 전달 */
//    public boolean payMovie(Payment payments) {
//        String query = QueryUtil.getQuery("payMovie");
//
//        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
//            ps.setString(1, payments.getPaymentMethod());
//
//            int affectedRows = ps.executeUpdate();
//            return affectedRows > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


}
