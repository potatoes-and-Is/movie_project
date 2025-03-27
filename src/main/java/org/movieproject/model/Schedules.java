package org.movieproject.model;

public class Schedules {

    private int scheduleId;
    private String scheduleStartTime;

    public Schedules(int scheduleId, String scheduleStartTime) {
        this.scheduleId = scheduleId;
        this.scheduleStartTime = scheduleStartTime;
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

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", scheduleStartTime='" + scheduleStartTime + '\'' +
                '}';
    }
}
