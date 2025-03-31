package org.movieproject.model;

public class Movies {
    private int movieId;
    private String movieTitle;
    private int moviePrice;

    public Movies(int movieId, String movieTitle, int moviePrice) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePrice = moviePrice;
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

    @Override
    public String toString() {
        return "Movies{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", moviePrice=" + moviePrice +
                '}';
    }
}
