package org.movieproject.model;

public class Seats {

    private int seatId;
    private String seatNumber;
    private char seatStatus;

    public Seats(int seatId, String seatNumber, char seatStatus) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public char getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(char seatStatus) {
        this.seatStatus = seatStatus;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", seatNumber='" + seatNumber + '\'' +
                ", seatStatus=" + seatStatus +
                '}';
    }
}
