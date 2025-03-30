package org.movieproject.dao;

import org.movieproject.model.Movie;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDao {
    private Connection connection;

    public MovieDao(Connection connection) {
        this.connection = connection;
    }

    // 전체 영화 목록을 조회하는 메서드
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = QueryUtil.getQuery("getAllMovies");
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery(sql)) {
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("movie_title"));
                movie.setPrice(rs.getInt("movie_price"));
                movies.add(movie);
            }
        }
        return movies;
    }

    // 영화 번호로 영화 정보를 조회하는 메서드
    public Movie getMovieById(int movieId) throws SQLException {
        String sql = QueryUtil.getQuery("getMovieById");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Movie movie = new Movie();
                    movie.setMovieId(rs.getInt("movie_id"));
                    movie.setTitle(rs.getString("movie_title"));
                    movie.setPrice(rs.getInt("movie_price"));
                    return movie;
                }
            }
        }
        return null; // 조회 결과가 없으면 null 반환
    }

}
