package org.movieproject.model;

public class TicketInfo {
    private int ticketId;
    private String movieTitle;
    private String scheduleStartTime;

    public TicketInfo(int ticketId, String movieTitle, String scheduleStartTime) {
        this.ticketId = ticketId;
        this.movieTitle = movieTitle;
        this.scheduleStartTime = scheduleStartTime;
    }

    public int getTicketId() {
        return ticketId;
    }
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public String getScheduleStartTime() {
        return scheduleStartTime;
    }
    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    @Override
    public String toString() {
        return "티켓 ID: " + ticketId +
                ", 영화: " + movieTitle +
                ", 상영시간: " + scheduleStartTime;
    }

}
