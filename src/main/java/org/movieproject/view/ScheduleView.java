package org.movieproject.view;

import org.movieproject.model.Schedule;
import org.movieproject.service.ScheduleService;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ScheduleView {
    private final ScheduleService scheduleService;
    private final Scanner scanner;
    private final Connection connection;

    public ScheduleView(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scheduleService = new ScheduleService(connection);
        this.scanner = scanner;
    }

    /**
     * 영화 ID에 해당하는 상영시간 목록을 화면에 출력하고,
     * 사용자가 올바른 상영시간 번호를 선택할 때까지 입력을 반복받는다.
     * 만약 해당 영화의 상영시간이 하나도 없다면, "다시 선택해주세요" 메시지를 출력하고 -1을 반환한다.
     *
     * @param movieId 선택된 영화의 ID
     * @return 사용자가 선택한 상영시간 ID; 만약 상영시간이 없으면 -1 반환
     */
    public int displaySchedules(int movieId) {
        try {
            List<Schedule> schedules = scheduleService.getSchedulesByMovieId(movieId);
            if (movieId == -1 || schedules == null || schedules.isEmpty()) {
                System.out.println("해당 영화의 상영시간이 없습니다. 다시 선택해주세요.");
                return -1;
            }

            System.out.println("===== 상영시간 선택 =====");
            Map<Integer, String> scheduleMap = new HashMap<>();

            for (Schedule s : schedules) {
                scheduleMap.put(s.getScheduleId(), s.getScheduleStartTime());
                for (Integer i : scheduleMap.keySet()) {
                    System.out.println(scheduleMap.get(i) + "\t" + scheduleMap.get(i) + "\t" + s.getScheduleStartTime());
                }
            }
            // 올바른 상영시간 번호 입력을 받을 때까지 반복
            while (true) {
                System.out.print("상영시간 번호 선택: ");
                String input = scanner.nextLine().trim();
                try {
                    int scheduleId = Integer.parseInt(input);
                    boolean valid = false;
                    for (Schedule s : schedules) {
                        if (s.getScheduleId() == scheduleId) {
                            valid = true;
                            break;
                        }
                    }
                    if (valid) {
                        return scheduleId;
                    } else {
                        System.out.println("유효하지 않은 상영시간 번호입니다. 다시 입력해주세요.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("숫자를 입력해주세요.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}
