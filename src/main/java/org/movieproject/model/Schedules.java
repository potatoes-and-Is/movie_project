package org.movieproject.model;

public class Schedules {
    private int scheduleId;
    private String startTime;
    private Movies movieId;

    public Schedules(int scheduleId, String startTime, Movies movieId) {
        this.scheduleId = scheduleId;
        this.startTime = startTime;
        this.movieId = movieId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Movies getMovieId() {
        return movieId;
    }

    public void setMovieId(Movies movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "영화 :" + movieId.getMovieTitle() + ", " + "가격 :" + movieId.getMoviePrice() + "상영 시작 시간 : " + startTime + "\n";
    }
}


