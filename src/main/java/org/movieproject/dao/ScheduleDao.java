package org.movieproject.dao;

import org.movieproject.model.Schedule;
import org.movieproject.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private Connection connection;

    public ScheduleDao(Connection connection) {
        this.connection = connection;
    }

    // 영화 ID에 해당하는 상영시간 목록 조회
    public List<Schedule> getSchedulesByMovieId(int movieId) throws SQLException {
        List<Schedule> list = new ArrayList<>();
        String sql = QueryUtil.getQuery("getSchedulesByMovieId");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setScheduleId(rs.getInt("schedule_id"));
                    schedule.setScheduleStartTime(rs.getString("schedule_start_time"));
                    schedule.setMovieId(rs.getInt("movie_id"));
                    list.add(schedule);
                }
            }
        }
        return list;
    }
}
