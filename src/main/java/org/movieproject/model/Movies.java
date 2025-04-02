package org.movieproject.model;

import java.sql.Timestamp;

public class Movies {
    private int movieId;
    private String movieTitle;
    private int moviePrice;
    private String scheduleStartTime;

    public Movies(int movieId, String movieTitle, int moviePrice) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePrice = moviePrice;
    }

    public Movies(String movieTitle, String scheduleStartTime) {
        this.movieTitle = movieTitle;
        this.scheduleStartTime = scheduleStartTime;
    }

    public Movies() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMoviePrice() {
        return moviePrice;
    }

    public void setMoviePrice(int moviePrice) {
        this.moviePrice = moviePrice;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", moviePrice=" + moviePrice +
                '}';
    }
}
