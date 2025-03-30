package org.movieproject.view;

import org.movieproject.model.Schedules;
import org.movieproject.service.ScheduleService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ScheduleView {
    private final ScheduleService scheduleService;
    private final Scanner scanner;

    /**
     * 📌 생성자 (UserView)
     * - UserService 객체를 생성하여 주입받고, 사용자 입력을 받기 위한 Scanner 객체 초기화
     *
     * @param connection 데이터베이스 연결을 위한 Connection 객체
     */
    public ScheduleView(Connection connection) {
        this.scheduleService = new ScheduleService(connection);
        this.scanner = new Scanner(System.in);
    }

    /**
     * 📌 영화 메뉴 출력
     * - 영화를 출력할 수 있도록 메뉴 목록 제공
     */
    public void showMenu() {
        System.out.println("영화 목록 조회");
        System.out.print("1번을 눌러 영화를 선택하세요: ");

        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> {
                    printAllMovies();
                    System.out.println("\n9를 입력하면 메뉴를 다시 시작합니다.");
                }
                case 9 -> {
                    showMenu();
                    return;
                }
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
            }
        }
    }


    /**
     * 📌 전체 영화 조회
     * - MovieService의 getAllMovies() 메서드를 호출하여 영화 목록을 출력
     */
    private void printAllMovies() {
        try {
            List<Schedules> schedules = scheduleService.getAllMovies();

            if (schedules.isEmpty()) {
                System.out.println("등록된 영화가 없습니다.");
            } else {
                System.out.println("\n===== 전체 영화 목록 =====");
                for (Schedules schedule : schedules) {
                    System.out.println(
                            schedule.getScheduleId() + " | " +
                                    schedule.getStartTime() + " | " +
                                    schedule.getMovieId().getMovieTitle() + " | " +
                                    schedule.getMovieId().getMoviePrice() + " 원"
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("영화 목록을 조회하는 중 오류가 발생했습니다.");
            e.printStackTrace(); // 디버깅용
        }
    }
}
