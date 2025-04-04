package org.movieproject.service;

import org.movieproject.dao.PaymentDao;
import org.movieproject.model.PayMethod;
import org.movieproject.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentDao paymentDao;
    private final Connection connection;

    public PaymentService(Connection connection) {
        this.connection = connection;
        this.paymentDao = new PaymentDao(connection);
    }

    /* 결제수단 목록 조회하기 */
    public List<PayMethod> getAllPayMethods(int userId) throws SQLException {
        List<PayMethod> payMethods = paymentDao.getAllPayMethods(userId);

        if (payMethods == null) {
            log.error("조회한 결제 수단의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            return null;
        }

        return payMethods;
    }

    /* 결제 수단 등록하기 */
    public boolean addPayMethod(PayMethod payMethod) throws SQLException {
        // 유효성 검사
        validatePayMethod(payMethod);

        boolean addSuccess = paymentDao.addPayMethod(payMethod);
        return addSuccess;
    }

    /* 결제 등록하기 */
    public boolean payMovie(Payment payment) throws SQLException {
        //검증 처리 필요
        validatePayment(payment);

        return paymentDao.payMovie(payment);
    }

    /* 이미 결제한 ticket_id 인지 확인하기 */
    public boolean isAlreadyPaid(int ticketId) {

        return paymentDao.isAlreadyPaid(ticketId);
    }

    /* 결제되지 않은 티켓 삭제하기 */
    public boolean cancelUnpaidTicket(int ticketId) {
        try {
            return paymentDao.cancelUnpaidTicket(ticketId);
        } catch (Exception e) {
            // 사용자 메시지 전달
            System.out.println("예매 취소 중 문제가 발생했습니다.");
            return false;
        }
    }

    /* 결제 정보 삭제 */
    public void deletePayment(int ticketId) {
        Payment payment = paymentDao.getPaymentById(ticketId);
        paymentDao.deletePayment(payment.getPaymentId());
    }

    /* 결제 수단 등록 시 유효성 검사 */
    private void validatePayMethod(PayMethod payMethod) {
        String cardNumber = payMethod.getPayMethodNumber();
        String password = payMethod.getPayMethodPwd();

        // 카드 번호 형식: 16자리 숫자 + '-' 3개 포함
        if (!cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            throw new IllegalArgumentException("카드 번호 형식이 올바르지 않습니다. (예: 1234-5678-9012-3456)");
        }

        // 비밀번호: 숫자 4자리
        if (!password.matches("\\d{4}")) {
            throw new IllegalArgumentException("비밀번호는 숫자 4자리여야 합니다.");
        }
    }

    /* 결제 등록 시 유효성 검사 */
    private void validatePayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("결제 정보가 없습니다.");
        }

        if (payment.getTicketId() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 티켓 정보입니다.");
        }

        if (payment.getPayMethodId() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 결제 수단입니다.");
        }

        if (payment.getPaymentPrice() <= 0) {
            throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다.");
        }
    }
}