package org.movieproject.dao;

import org.junit.jupiter.api.*;
import org.movieproject.config.JDBCConnection;
import org.movieproject.model.Payment;

import java.sql.Connection;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentDaoTest {

    private Connection connection;
    private PaymentDao paymentDao;
    private Payment payment;

    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            paymentDao = new PaymentDao(connection);
        } catch (SQLException e) {
            Assertions.fail("데이터베이스 연결 실패");
        }
    }

    @Test
    @DisplayName("티켓 ID로 결제 정보 조회")
    void getPaymentById() {
        payment = paymentDao.getPaymentById(40);
        System.out.println("조회된 결제 건: " + payment.getPaymentId());

        Assertions.assertNotNull(payment, "조회한 Payment 객체가 null이 아니어야 합니다.");
        Assertions.assertEquals(19, payment.getPaymentId(), "조회한 결제 ID가 일치해야 합니다.");
    }


//    @Test
//    @DisplayName("결제 ID로 결제 정보 삭제")
//    void deletePayment() {
//        payment = paymentDao.getPaymentById(39);
//        System.out.println("삭제된 결제 건: " + payment.getPaymentId());
//        boolean boolPayment = paymentDao.deletePayment(payment.getPaymentId());
//
//        Assertions.assertTrue(boolPayment);
//    }

    @Test
    void tearDown() {
        try {
            connection.close();
            System.out.println("테스트 데이터 롤백 완료");
        } catch (SQLException e) {
            Assertions.fail("트랜잭션 롤백 실패" + e.getMessage());
        }
    }
}