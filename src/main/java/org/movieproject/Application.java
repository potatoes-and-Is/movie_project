package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.PaymentView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        int ticket_id = 1;
        int user_id = 2;

        PaymentView paymentView = new PaymentView(connection);

        paymentView.getAllPayMethods();
    }
}
