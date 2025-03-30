package org.movieproject.dao;

import org.movieproject.model.Seat;
import org.movieproject.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatsDao {

    private final Connection connection;

    public SeatsDao(Connection connection) {
        this.connection = connection;
    }

    // 전체 좌석 조회
    public List<Seat> getAllSeats() throws SQLException {
        List<Seat> list = new ArrayList<>();
        String sql = QueryUtil.getQuery("getAllSeats");
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                list.add(seat);
            }
        }
        return list;
    }

    // 특정 상영시간에 예약되어 사용 불가능한 좌석 조회 (Cinema_Infos 조인)
    public List<Seat> getUnavailSeats(int scheduleId) throws SQLException {
        List<Seat> list = new ArrayList<>();
        String sql = QueryUtil.getQuery("getUnavailSeatsByScheduleId");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setSeatNumber(rs.getString("seat_number"));
                    list.add(seat);
                }
            }
        }
        return list;
    }


}