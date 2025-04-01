package org.movieproject.model;

public class Schedules {
    private int scheduleId;
    private String startTime;
    private int movieId;

    public Schedules(int scheduleId, String startTime, int movieId) {
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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

}


