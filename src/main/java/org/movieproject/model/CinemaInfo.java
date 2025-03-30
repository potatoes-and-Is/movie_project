package org.movieproject.model;

public class CinemaInfo {
    private int cinemaInfoId;
    private int scheduleId;
    private int seatId;

    public CinemaInfo() {}

    public CinemaInfo(int cinemaInfoId, int scheduleId, int seatId) {
        this.cinemaInfoId = cinemaInfoId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
    }

    // Getters & Setters
    public int getCinemaInfoId() {
        return cinemaInfoId;
    }
    public void setCinemaInfoId(int cinemaInfoId) {
        this.cinemaInfoId = cinemaInfoId;
    }
    public int getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
    public int getSeatId() {
        return seatId;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    @Override
    public String toString() {
        return "CinemaInfo{" +
                "cinemaInfoId=" + cinemaInfoId +
                ", scheduleId=" + scheduleId +
                ", seatId=" + seatId +
                '}';
    }


}
