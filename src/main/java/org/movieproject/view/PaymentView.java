package org.movieproject.view;

import org.movieproject.model.PayMethod;
import org.movieproject.service.PaymentService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PaymentView {
    private final PaymentService paymentService;
    private final Scanner scanner;

    public PaymentView(Connection connection) {
        this.paymentService = new PaymentService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void payMovie(int ticket_id, int user_id) {
        System.out.println("===== 결제를 진행하시겠습니까? =====");


//        System.out.println("1. 카드");
//        System.out.println("2. 현금");
//
//        String paymentMethod = scanner.nextLine();
//        if (paymentMethod.equals("1")) {
//            paymentMethod = "카드";
//        } else {
//            paymentMethod = "현금";
//
//        }
//
//        Payments payments = new Payments(0, paymentMethod, null, 0);
//        try {
//            int re = 1;
//          boolean success = paymentsService.payMovie(payments);
//            if (success) {
//                System.out.println("결제가 성공적으로 완료되었습니다.");
//            } else {
//                System.out.println("결제에 실패하였습니다.");
//            }
//
//        } catch (Exception e) {
//            System.out.println("결제 진행 중 오류가 발생했습니다.");
//        }

    }


    /* 결제 수단 목록 조회 */
    public void getAllPayMethods() {
        try {
            List<PayMethod> payMethods = paymentService.getAllPayMethods();

            if (payMethods.isEmpty()) {
                System.out.println("등록된 결제 수단이 없습니다.");
            } else {
                System.out.println("\n===== 전체 결제 수단 목록 =====");
                payMethods.forEach(payMethod -> System.out.println(
                        payMethod.getPayMethodId() +") "
                        + payMethod.getPayMethodNumber() +" (잔액 : "
                        + payMethod.getPayMethodBalance() +"원)"));
            }

        } catch (SQLException e) {
            System.out.println("결제 수단 목록을 조회하는 중 오류가 발생했습니다.");
        }
    }




}
