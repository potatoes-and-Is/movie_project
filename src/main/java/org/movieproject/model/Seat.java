package org.movieproject.model;

import java.util.Objects;

public class Seat {

    private int seatId;
    private String seatNumber;

    public Seat() {
    }

    public Seat(int seatId, String seatNumber) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
    }

    public Seat(String space) {
        this.seatNumber = space;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 자기 자신과 비교 시 true
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;

        // [중요] seatId, seatNumber 타입에 따라 비교 방법 달라짐
        // seatId가 int라면 == 사용, seatNumber가 String이라면 Objects.equals(...) 사용
        return seatId == seat.seatId && Objects.equals(seatNumber, seat.seatNumber);
        // 필요하면 seatRow, seatCol 등 다른 필드도 추가
    }

    @Override
    public int hashCode() {
        // equals에서 비교한 필드는 모두 hashCode에도 포함
        return Objects.hash(seatId, seatNumber);
    }

    @Override
    public String toString() {
        return "좌석 : " + seatNumber;
    }
}
