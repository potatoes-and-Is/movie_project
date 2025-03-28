package org.movieproject.model;

public class Schedules {

    private int scheduleId;
    private String scheduleStartTime;
    private int movieId;

    public Schedules(int scheduleId, String scheduleStartTime, int movieId) {
        this.scheduleId = scheduleId;
        this.scheduleStartTime = scheduleStartTime;
        this.movieId = movieId;
    }

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
        return "Schedules{" +
                "scheduleId=" + scheduleId +
                ", scheduleStartTime='" + scheduleStartTime + '\'' +
                ", movieId=" + movieId +
                '}';
    }
}
