package org.movieproject;

import org.movieproject.config.JDBCConnection;
import org.movieproject.view.SchedulesView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        System.out.println("프로젝트 환경 테스트");

        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("번호 선택");
            System.out.println("1. 영화 선택하기");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showMovieSchedules(connection);
            }
        }
    }

    private static void showMovieSchedules(Connection connection) {
        SchedulesView schedulesView = new SchedulesView(connection);
        schedulesView.showSchedules();
    }

}
