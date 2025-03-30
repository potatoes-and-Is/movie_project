package org.movieproject.view;

import org.movieproject.service.PaymentService;

import java.sql.Connection;
import java.util.Scanner;

public class PaymentView {

    private PaymentService paymentService;
    private Scanner scanner;

    public PaymentView(Connection connection, Scanner scanner) {
        this.paymentService = new PaymentService(connection);
        this.scanner = scanner;
    }

    // 결제 수단 선택 및 결제 처리 예시
    public int processPayment() {
        System.out.println("===== 결제 수단 선택 =====");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        System.out.println("3. Mobile Payment");
        System.out.print("결제 수단 선택: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        String paymentMethod;
        switch (choice) {
            case 1: paymentMethod = "Credit Card"; break;
            case 2: paymentMethod = "Cash"; break;
            case 3: paymentMethod = "Mobile Payment"; break;
            default: paymentMethod = "Cash";
        }
        System.out.print("결제 금액 입력: ");
        int price = Integer.parseInt(scanner.nextLine().trim());
        int paymentId = paymentService.processPayment(paymentMethod, price);
        return paymentId;
    }
}
