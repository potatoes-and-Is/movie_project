package org.movieproject.view;

import org.movieproject.service.MovieService;
import org.movieproject.service.PaymentService;

import java.sql.Connection;
import java.util.Scanner;

public class PaymentView {

    private PaymentService paymentService;
    private MovieService movieService;
    private Scanner scanner;

    public PaymentView(Connection connection, Scanner scanner) {
        this.paymentService = new PaymentService(connection);
        this.scanner = scanner;
    }

    // 결제 수단 선택 및 결제 처리 예시
    public int processPayment() {
        System.out.println("===== 결제 수단 선택 =====");

        System.out.println("1. 카드");
        System.out.println("2. 현금");
        System.out.print("결제 수단 선택: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());

        String paymentMethod = "";
        if(choice == 1) {
            paymentMethod = "카드";
        } else if(choice == 2) {
            paymentMethod = "현금";
        } else {
            System.out.println("결제가 진행되지 않았습니다. 다시 진행하여 주시기 바랍니다.");
            return -1;
        }
        int price = Integer.parseInt(scanner.nextLine().trim());
        int paymentId = paymentService.processPayment(paymentMethod, price);
        return paymentId;
    }
}
