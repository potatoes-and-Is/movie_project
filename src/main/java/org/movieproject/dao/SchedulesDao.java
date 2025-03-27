package org.movieproject.dao;

import org.movieproject.model.Schedules;
import org.movieproject.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchedulesDao {
    private final Connection connection;

    public SchedulesDao(Connection connection) {
        this.connection = connection;
    }

    // 특정 영화 ID의 상영 시간 조회
    public List<Schedules> getSchedulesBymovieId(int movieId) {
        List<Schedules> schedules = new ArrayList<>();
        String query = QueryUtil.getQuery("getSchedulesBymovieId");

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                schedules.add(new Schedules(
                        rs.getInt("schedule_id"),
                        rs.getString("schedule_start_time")
                ));
            }
        } catch (SQLException e) {
            System.out.println("getSchedulesBymovieId: " + e.getMessage());
        }
        return schedules;
    }
}
