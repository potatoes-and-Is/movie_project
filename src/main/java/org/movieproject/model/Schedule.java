package org.movieproject.model;

public class Schedule {
    private int scheduleId;
    private String scheduleStartTime;
    private int movieId; // Movies 테이블과의 외래키

    public Schedule() {
    }

    public Schedule(int scheduleId, String scheduleStartTime, int movieId) {
        this.scheduleId = scheduleId;
        this.scheduleStartTime = scheduleStartTime;
        this.movieId = movieId;
    }

    // Getters & Setters
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", scheduleStartTime='" + scheduleStartTime + '\'' +
                ", movieId=" + movieId +
                '}';
    }
}



