package org.movieproject.dao;

import org.movieproject.model.Reservations;
import org.movieproject.model.Tickets;
import org.movieproject.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationsDAO {

    private final Connection connection;

    public ReservationsDAO(Connection connection) {
        this.connection = connection;
    }

    public Reservations getReservationsById(int reservationId) {
        String query = QueryUtil.getQuery("getReservationsById");
        Reservations reservations = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, reservationId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                reservations = new Reservations(
                        rs.getInt("reservation_id"),
                        rs.getInt("seat_id"),
                        rs.getInt("movie_info_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // 예약 ID에 대한 정보 삭제
    public boolean deleteReservation(int reservationId) {
        String query = QueryUtil.getQuery("deleteReservation");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, reservationId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
