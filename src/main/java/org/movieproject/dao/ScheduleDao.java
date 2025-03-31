package org.movieproject.dao;

import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private final Connection connection;

    public ScheduleDao(Connection connection) {
        this.connection = connection;
    }

    // 전체 영화 출력
    public List<Schedules> getAllMoviesSchedule() throws SQLException {
        List<Schedules> schedules = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllSchedule"); // XML에서 쿼리 로드

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery(query)) {

            while (rs.next()) {
                schedules.add(new Schedules(
                        rs.getInt("schedule_id"),
                        rs.getString("schedule_start_time"),
                        rs.getInt("movie_id") // Movies => int
                ));
            }
        }
        return schedules;
    }

    // 영화Id 값으로 모든 영화 가지고 오기
    public Movies getMovieById(int movieId) throws SQLException {

        String query = QueryUtil.getQuery("getMovieById");
        Movies movie = new Movies();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
              movie = new Movies(
                        rs.getInt("movie_id"),
                        rs.getString("movie_title"),
                        rs.getInt("movie_price")
                );
            }
        }
        return movie;
    }
}
