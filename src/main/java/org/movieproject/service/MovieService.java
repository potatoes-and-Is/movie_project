package org.movieproject.service;

import org.movieproject.dao.MovieDao;
import org.movieproject.model.Movie;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MovieService {
    private final MovieDao movieDao;

    public MovieService(Connection connection) {
        this.movieDao = new MovieDao(connection);
    }

    // 전체 영화 목록 조회
    public List<Movie> getAllMovies() {
        try {
            return movieDao.getAllMovies();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 영화 상세 정보 조회
    public Movie getMovieById(int movieId) {
        try {
            return movieDao.getMovieById(movieId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
