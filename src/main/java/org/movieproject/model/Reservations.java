package org.movieproject.model;

public class Reservations {

    private int reservationId;
    private int seatId;
    private int movieInfoId;

    public Reservations(int reservationId, int seatId, int movieInfoId) {
        this.reservationId = reservationId;
        this.seatId = seatId;
        this.movieInfoId = movieInfoId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getMovieInfoId() {
        return movieInfoId;
    }
}
