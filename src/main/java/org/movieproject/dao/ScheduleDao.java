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
                Movies movie = new Movies(
                        rs.getInt("movie_id"),
                        rs.getString("movie_title"),
                        rs.getInt("movie_price")
                );

                schedules.add(new Schedules(
                        rs.getInt("schedule_id"),
                        rs.getString("schedule_start_time"),
                        movie
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }


    // 스케쥴 추가
//    public boolean addMovies(Schedules movies) throws SQLException {
//        String query = QueryUtil.getQuery("addSchedule") ;
////        String query = "insert into movies (movieTitle, moviePrice) values (?, ?)";
//
//        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setString(1, movies.getMovieTitle());
//            ps.setInt(2, movies.getMoviePrice());
//            ps.setInt(3, movies.getScheduleId());
//            ps.setString(4, movies.getScheduleStartTime());
//
//            int affectedRows = ps.executeUpdate();
//            return affectedRows > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
