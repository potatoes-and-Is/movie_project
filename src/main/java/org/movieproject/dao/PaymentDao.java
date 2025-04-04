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
                             rs.getString("pay_method_pwd")
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
            ps.setString(3, payMethod.getPayMethodPwd());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /* 결제 등록하기 */
    public boolean payMovie(Payment payment) {
        String query = QueryUtil.getQuery("payMovie");

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getPaymentPrice());
            ps.setInt(2, payment.getTicketId());
            ps.setInt(3, payment.getPayMethodId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* 결제 가능 여부 확인하기 */
    public boolean isAlreadyPaid(int ticketId) {
        String query = QueryUtil.getQuery("isAlreadyPaid");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ticketId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("cnt");
                    return count > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /* 다시 영화정보 선택으로 돌아가기 위해 선택한 ticket, cinema_info 삭제하기  */
    public boolean cancelUnpaidTicket(int ticketId) {
        String query = QueryUtil.getQuery("cancelUnpaidTicket");

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticketId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Payment getPaymentById(int ticketId) {
        String query = QueryUtil.getQuery("getPaymentById");
        Payment payment = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ticketId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                payment = new Payment(
                        rs.getInt("payment_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }

    /* 취소한 티켓 결제 정보 삭제 */
    public void deletePayment(int paymentId) {
        String query = QueryUtil.getQuery("deletePayment");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, paymentId);

            int affectedRows = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}