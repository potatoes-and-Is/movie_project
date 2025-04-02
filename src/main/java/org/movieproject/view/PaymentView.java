package org.movieproject.view;

import org.movieproject.model.PayMethod;
import org.movieproject.model.Payment;
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

//    public void payMovie(int ticket_id, int user_id) {
//        System.out.println("===== 결제를 진행하시겠습니까? =====");


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

//    }

    /* 결제 과정 시작 */
    public void startPay(int ticketId, int userId) {
        getAllPayMethods(userId);

    }


    /* 결제 수단 목록 조회 */
    public void getAllPayMethods(int userId) {
        try {
            List<PayMethod> payMethods = paymentService.getAllPayMethods(userId);

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

    /* 결제 수단 등록하기 */
    public void addPayMethod(int userId) {
        System.out.println("\n======== 결제 수단 등록 ========");
        System.out.print("카드 번호 16자리 숫자를 '-'를 포함하여 입력하세요 \n (예시: 1234-5678-9012-3456) : ");
        String inputMethodNum = scanner.nextLine();

        System.out.print("비밀번호 4자리를 입력하세요 : ");
        String inputMethodPwd = scanner.nextLine();

        PayMethod payMethod = new PayMethod(0, inputMethodNum, 0, userId, inputMethodPwd);
        try {
            boolean addSuccess = paymentService.addPayMethod(payMethod);
            if (addSuccess) {
                System.out.println("결제 수단이 성공적으로 등록되었습니다.");
            } else {
                System.out.println("결제 수단 등록에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.out.println("결제 수단 등록 중 오류가 발생했습니다.");
        }
    }

    /* 결제 등록하기 */
    public void payMovie(int ticketId) {
        System.out.print("\n결제를 위한 결제 수단을 선택해 주세요 : ");
        String inputStrPayNum = scanner.nextLine();
        int inputPayNum = Integer.parseInt(inputStrPayNum);

        Payment payment = new Payment(0, 19000, null, ticketId, inputPayNum);
        try {
            boolean paySuccess = paymentService.payMovie(payment);
            if (paySuccess) {
                System.out.println("결제가 성공적으로 완료되었습니다.");
            } else {
                System.out.println("결제에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.out.println("결제 진행 중 오류가 발생했습니다.");
        }
    }


}
