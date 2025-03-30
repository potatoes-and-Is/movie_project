package org.movieproject.service;

import org.movieproject.dao.ReservationsDAO;
import org.movieproject.model.Reservations;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservationsService {

    private final ReservationsDAO reservationsDAO;
    private final Connection connection;

    public ReservationsService(Connection connection) {
        this.connection = connection;
        this.reservationsDAO = new ReservationsDAO(connection);
    }

    // 예약 ID로 예약 정보 존재하는지 확인
    public Reservations getReservationsById(int reservationId) {
        Reservations reservations = reservationsDAO.getReservationsById(reservationId);

        if (reservations == null) {
            throw new IllegalArgumentException("예약 정보를 찾을 수 없습니다.");
        }
        return reservations;
    }

    public boolean deleteReservation(int reservationId) {
        // 예약 ID로 예약 정보 존재하는지 확인
        Reservations reservations = getReservationsById(reservationId);

        // 예약 정보 삭제 중 오류 발생할 경우
        if (reservations == null) {
            throw new IllegalArgumentException("삭제할 예약 정보가 없습니다.");
        }
        return reservationsDAO.deleteReservation(reservationId);
    }
}
