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
//    private final MovieView movieView;
    private final Scanner scanner;

    public PaymentView(Connection connection) {
        this.paymentService = new PaymentService(connection);
//        this.movieView = new MovieView(connection);
        this.scanner = new Scanner(System.in);
    }

    /* 결제 과정 시작 */
    public void showPaymentMenu(int ticketId, int userId) {
        while (true) {
            System.out.println("\n===== 결제 관리 메뉴 =====");
            System.out.println("결제는 등록된 결제수단을 통해서만 가능합니다.");
            System.out.println("1. 결제하기 (결제수단 목록 조회 후 결제)");
            System.out.println("2. 결제수단 등록하기");
            System.out.println("3. 결제수단 목록만 조회하기");
            System.out.println("4. 메인으로 이동하기");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    getAllPayMethods(ticketId, userId);
                    break;
                case 2:
                    addPayMethod(userId, ticketId);
                    printPayMethodList(userId);
                    break;
                case 3:
                    printPayMethodList(userId);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }

        }

    }

    /* 결제 흐름용 전용 함수 (switch case 1: 에서 실행됨) */
    /* 결제 수단 목록 조회 */
    public void getAllPayMethods(int ticketId, int userId) {
            while (true) {
                try {
                    List<PayMethod> payMethods = paymentService.getAllPayMethods(userId);
                    if (payMethods.isEmpty()) {
                        System.out.println("\n등록된 결제 수단이 없습니다. 결제 수단 등록을 진행합니다.");
                        addPayMethod(userId, ticketId);
                        continue; // addPayMethod 로 수단 등록 후, return으로 돌아왔을 때 반복문 처음으로 이동하기!
                    } else {
                        System.out.println("\n===== 전체 결제 수단 목록 =====");
                        payMethods.forEach(payMethod -> System.out.println(
                                payMethod.getPayMethodId() + ") "
                                        + payMethod.getPayMethodNumber() + " (잔액 : "
                                        + payMethod.getPayMethodBalance() + "원)"));
                        // 결제하기
                        payMovie(ticketId);
                        return;
                    }

                } catch (SQLException e) {
                    System.out.println("결제 수단 목록을 조회하는 중 오류가 발생했습니다.");
                }
            }
    }

    /* 오직 순수한 결제 수단 목록만 조회 후 출력하는 함수 */
    /* 결제 수단 목록 조회 */
    public void printPayMethodList(int userId) {
        try {
            List<PayMethod> payMethods = paymentService.getAllPayMethods(userId);
            if (payMethods.isEmpty()) {
                System.out.println("\n등록된 결제 수단이 없습니다. 결제 수단 등록을 진행합니다.");
            } else {
                System.out.println("\n===== 전체 결제 수단 목록 =====");
                payMethods.forEach(payMethod -> System.out.println(
                        payMethod.getPayMethodId() + ") "
                                + payMethod.getPayMethodNumber() + " (잔액 : "
                                + payMethod.getPayMethodBalance() + "원)"));
            }
        } catch (SQLException e) {
            System.out.println("결제 수단 목록을 조회하는 중 오류가 발생했습니다.");
        }
    }

    /* 결제 수단 등록하기 */
    public void addPayMethod(int userId, int ticketId) {
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
                return;
            } else {
                System.out.println("결제 수단 등록에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.out.println("결제 수단 등록 중 오류가 발생했습니다.");
        }
    }

    /* 결제 등록하기 */
    public void payMovie(int ticketId) {
        try {
            // 이미 결제된 티켓인지 확인하기 (결제된 거면 결제수단 입력 받을 필요도 없음)
            if (paymentService.isAlreadyPaid(ticketId)) {
                System.out.println("이미 결제가 완료된 티켓입니다.");
                return; // case 1 에서 목록 조회 후, 무조건 결제되는 것 방지
            }

            // 결제되지 않은 티켓의 결제 정상 진행
            System.out.print("\n결제를 위한 결제 수단을 선택해 주세요 : ");
            String inputStrPayNum = scanner.nextLine();
            int inputPayNum = Integer.parseInt(inputStrPayNum);

            Payment payment = new Payment(0, 19000, null, ticketId, inputPayNum);

            boolean paySuccess = paymentService.payMovie(payment);
            if (paySuccess) {
                System.out.println("결제가 성공적으로 완료되었습니다.");
                return;
            } else {
                System.out.println("결제에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.out.println("결제 진행 중 오류가 발생했습니다.");
        }
    }

    /* MovieView의 showMenu(Users loginUser) 로 이동하기 */


}
