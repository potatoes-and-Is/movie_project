package org.movieproject.model;

public class Movie {
    private int movieId;
    private String title;
    private int price;


    public Movie() {
    }

    public Movie(int movieId, String title, int price) {
        this.movieId = movieId;
        this.title = title;
        this.price = price;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
