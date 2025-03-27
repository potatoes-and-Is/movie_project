package org.movieproject.view;

import org.movieproject.model.Schedules;
import org.movieproject.service.SchedulesService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchedulesView {
    private final SchedulesService schedulesService;
    private final Scanner scanner;

    public SchedulesView(Connection connection) {
        this.schedulesService = new SchedulesService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void showSchedules() {
        while (true) {
            System.out.println("\n===== 보고싶은 영화를 선택해주세요. =====");
            System.out.println("1. 어벤져스");
            System.out.println("2. 라푼젤");
            System.out.println("3. 뒤로 가기");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> getScheduleByMovieId(choice);
                case 2 -> getScheduleByMovieId(choice);
                case 3 -> {
                    System.out.println("뒤로 가기");
                    return;
                }
            }
        }
    }

    // 사용자가 입력한 영화번호로 상영시간을 가져오는 메서드
    private void getScheduleByMovieId(int choice) {
        List<Schedules> schedules =  new ArrayList<>();
        System.out.println("\n===== 해당 영화의 상영 정보입니다. 시간을 선택해주세요. =====");
        try{
            schedules = schedulesService.getScheduleByMovieId(choice);
        } catch (SQLException e) {
            System.out.println();
        }
        for(int i=0; i<schedules.size();i++){
            System.out.println((i+1)+". " + schedules.get(i).getScheduleStartTime());
        }
    }
}

