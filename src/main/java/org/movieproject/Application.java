package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.TicketsView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.println("예매 내역 보러가기");
        System.out.print("선택 : ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> startTicketsManager(connection);
            case 0 -> {
                connection.close();
                System.out.println("프로그램을 종료합니다.");
            }
            default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    public static void startTicketsManager(Connection connection) throws SQLException {
        TicketsView ticketsView = new TicketsView(connection);
        int userId = 1;
        ticketsView.cancelTicketList(userId);
    }
}
