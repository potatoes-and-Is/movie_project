package org.movieproject.view;

import org.movieproject.service.PaymentService;

import java.sql.Connection;
import java.util.Scanner;

public class PaymentsView {
    private final PaymentService paymentsService;
    private final Scanner scanner;

    public PaymentsView(Connection connection) {
        this.paymentsService = new PaymentService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void payMovie(int ticket_id, int user_id) {
        System.out.println("===== 결제를 진행하시겠습니까? =====");
        System.out.println(user_id + "님의 등록된 결제 수단 목록입니다.");


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




}
