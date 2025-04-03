package org.movieproject.view;

import org.movieproject.service.MovieService;
import org.movieproject.service.PaymentService;

import java.sql.Connection;
import java.util.Scanner;

public class PaymentView {

    private final PaymentService paymentService;
    private final MovieService movieService;
    private final Scanner scanner;

    public PaymentView(Connection connection, Scanner scanner) {
        this.paymentService = new PaymentService(connection);
        this.scanner = scanner;
        this.movieService = new MovieService(connection);
    }

    // 결제 수단 선택 및 결제 처리 예시
    public int processPayment(int movieId) {
        System.out.println("===== 결제 수단 선택 =====");

        System.out.println("1. 카드");
        System.out.println("2. 현금");
        System.out.print("결제 수단 선택: ");
        String paymentMethod = getPaymentMethod(); // 결제 수단
        if (paymentMethod == null) return -1;

        return getPaymentId(movieId, paymentMethod); // ID 반환 0이면 뒤로가기 -1 인경우 재 진행
    }

    private int getPaymentId(int movieId, String paymentMethod) {
        String input;
        int choice;
        int price = movieService.getMovieById(movieId).getPrice();
        System.out.println("금액은 " + price + "원 입니다.");
        System.out.println("결제 하시겠습니까 ? 1. 예 2. 아니오");
        input = scanner.nextLine();
        choice = Integer.parseInt(input);
        if(choice == 1) {
            return paymentService.processPayment(paymentMethod, price);
        } else if(choice == 2) {
            return 0;
        } else {
            System.out.println("올바른 숫자를 다시 입력해주세요.");
        }
        return -1;
    }

    private String getPaymentMethod() {
        int choice;
        String input;
        input = scanner.nextLine();
        choice = Integer.parseInt(input);

        String paymentMethod = "";
        if(choice == 1) {
            paymentMethod = "카드";
        } else if(choice == 2) {
            paymentMethod = "현금";
        } else {
            System.out.println("결제가 진행되지 않았습니다. 다시 진행하여 주시기 바랍니다.");
            return null;
        }
        return paymentMethod;
    }
}
