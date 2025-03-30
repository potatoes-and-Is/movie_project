package org.movieproject.dao;


import org.movieproject.util.QueryUtil;

import java.sql.*;

public class PaymentDao {

    private final Connection connection;

    public PaymentDao(Connection connection) {
        this.connection = connection;
    }

    // 결제 정보 등록 후 생성된 payment_id 반환
    public int insertPayment(String paymentMethod, int paymentPrice) throws SQLException {
        String sql = QueryUtil.getQuery("insertPayment");
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, paymentMethod);
            pstmt.setInt(2, paymentPrice);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("결제 등록 실패");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("payment_id 생성 실패");
                }
            }
        }
    }

}
