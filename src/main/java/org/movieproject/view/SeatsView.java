package org.movieproject.view;

import org.movieproject.model.Seats;
import org.movieproject.service.SeatsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeatsView {
    private final SeatsService seatsService;
    private final Scanner scanner;

    public SeatsView(Connection connection) {
        this.seatsService = new SeatsService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while(true){
            System.out.println("\n===== 관람 원하시는 영화를 선택해주세요. =====");
            System.out.println("1. 10:00 어벤져스");
            System.out.println("2. 12:00 라푼젤");
            System.out.println("3. 14:00 어벤져스");
            System.out.println("4. 16:00 라푼젤");
            System.out.println("5. 18:00 About Time");
            int scheduleChoice = scanner.nextInt();
            scanner.nextLine();

            showSeats(scheduleChoice);
        }
    }

    // 선택 가능한 좌석 출력
    private void showSeats(int scheduleChoice) {
        System.out.println("\n===== 다음은 예약 가능한 좌석입니다. =====");
        try {
            int cnt = 0;
            List<Seats> seats = seatsService.getSeatsbyScheduleId(scheduleChoice);
            for(int i=0; i<seats.size();i++){
                for(int j=0; j<5; j++) {
                    if(cnt > seats.size()-1){
                        break;
                    } else {
                        System.out.print("["+seats.get(cnt).getSeatNumber()+"]   ");
                        cnt++;
                    }
                }
                System.out.println();
            }
        } catch(Exception e){
            System.out.println("showSeats:"+e.getMessage());
        }
        System.out.println("\n===== 좌석 번호를 선택해주세요. =====");
    }

}
