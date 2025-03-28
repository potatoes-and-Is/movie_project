package org.movieproject;

import org.movieproject.config.JDBCConnection;

import java.sql.Connection;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

    }
}
