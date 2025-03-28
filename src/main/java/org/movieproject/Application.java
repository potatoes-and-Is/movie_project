package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.ScheduleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        ScheduleView movieView = new ScheduleView(connection);
        movieView.showMenu();
    }
}
