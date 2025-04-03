package org.movieproject.view;

import org.movieproject.model.Schedule;
import org.movieproject.service.ScheduleService;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ScheduleView {
    private final ScheduleService scheduleService;
    private final Scanner scanner;
    private final Connection connection;
    // 입력 포맷 : DB나 내부 도메인에서는 "yyyy-MM-dd HH:mm:ss" 형식을 사용한다고 가정
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // 출력 포맷 : 분 단위로 포매팅 ("yyyy-MM-dd HH:mm")
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
            List<Schedule> schedules = scheduleService.getSchedulesByMovieId(movieId); // id에 해당하는 상영시간표 반환

            if (movieId == -1 || schedules == null || schedules.isEmpty()) {
                System.out.println("해당 영화의 상영시간이 없습니다. 다시 선택해주세요.");
                return -1;
            }
            System.out.println("===== 상영시간 선택(뒤로가기: 0) ======");
            Map<Integer, String> scheduleMap = listToMap(schedules); // 영화별 스케쥴을 Map 으로 변환
            printScheduleOptions(scheduleMap); // 상영시간표 출력
            return returnScheduleId(schedules, scheduleMap); // 상영시간 ID 반환
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    // 스케쥴 ID 반환
    private int returnScheduleId(List<Schedule> schedules, Map<Integer, String> scheduleMap) {
        // 올바른 상영시간 번호 입력을 받을 때까지 반복
        while (true) {
            System.out.print("상영시간 번호 선택: ");
            String input = scanner.nextLine().trim(); // 상영 시간 번호 입력
            int selectedIndex;

            try {
                selectedIndex = Integer.parseInt(input);
                if(selectedIndex == 0){
                    return -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
                continue;  // 숫자가 아닌 다른 값을 입력하면 다시
            }
            if (!scheduleMap.containsKey(selectedIndex)) {
                System.out.println("유효하지 않은 상영시간 번호입니다. 다시 입력해주세요.");
                continue; // 상영시간 번호가 아닌 다른 번호를 선택했을 때 다시
            }
            return mapToList(scheduleMap, selectedIndex, schedules); // map, index, schedule
        }
    }

// 선택한 번호에 해당하는 분 단위로 포매팅된 상영 시작 시간과 일치하는 Schedule의 id 반환
    private static int mapToList(Map<Integer, String> scheduleMap, int selectedIndex, List<Schedule> schedules) {
        if (scheduleMap.containsKey(selectedIndex)) {
            String selectedFormattedTime = scheduleMap.get(selectedIndex);
            // schedules List 내 각 Schedule의 시간 문자열을 분 단위로 포매팅하여 비교
            for (Schedule s : schedules) {
                String formattedTime = formatScheduleTime(s.getScheduleStartTime());
                if (formattedTime.equals(selectedFormattedTime)) {
                    return s.getScheduleId();
                }
            }
        }
        return -1;
    }

    // List<Schedule>을 순서를 유지하는 LinkedHashMap 형태로 변환
    // (key: 1부터 순차번호, value: 상영 시작 시간)
    private static Map<Integer, String> listToMap(List<Schedule> schedules) {

        // schedules를 시간(분 단위) 기준으로 정렬
        schedules.sort(Comparator.comparing(s -> {
            LocalDateTime dateTime = LocalDateTime.parse(s.getScheduleStartTime(), INPUT_FORMATTER);
            return dateTime.truncatedTo(ChronoUnit.MINUTES);
        }));

        Map<Integer, String> scheduleMap = new LinkedHashMap<>();
        int index = 1;
        for (Schedule s : schedules) {
            String formattedTime = formatScheduleTime(s.getScheduleStartTime());
            scheduleMap.put(index++, formattedTime);
        }
        return scheduleMap;
    }

    // Map의 내용을 이용해 상영시간 옵션을 출력
    private void printScheduleOptions(Map<Integer, String> scheduleMap) {
        for (Map.Entry<Integer, String> entry : scheduleMap.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }


    // 입력된 상영 시작 시간 문자열("yyyy-MM-dd HH:mm:ss")을 분 단위("yyyy-MM-dd HH:mm")로 포매팅하여 반환
    private static String formatScheduleTime(String scheduleStartTime) {
        LocalDateTime dateTime = LocalDateTime.parse(scheduleStartTime, INPUT_FORMATTER);
        LocalDateTime truncatedTime = dateTime.truncatedTo(ChronoUnit.MINUTES);
        return truncatedTime.format(OUTPUT_FORMATTER);
    }
}