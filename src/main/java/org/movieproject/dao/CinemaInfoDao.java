package org.movieproject.dao;

import org.movieproject.model.CinemaInfo;
import org.movieproject.util.QueryUtil;

import java.sql.*;

public class CinemaInfoDao {
    private final Connection connection;

    public CinemaInfoDao(Connection connection) {
        this.connection = connection;
    }

    // cinema_info_id로 조회 (기존)
    public CinemaInfo getCinemaInfoById(int cinemaInfoId) throws SQLException {
        String sql = QueryUtil.getQuery("getCinemaInfoById");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaInfoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CinemaInfo ci = new CinemaInfo();
                    ci.setCinemaInfoId(rs.getInt("cinema_info_id"));
                    ci.setScheduleId(rs.getInt("schedule_id"));
                    ci.setSeatId(rs.getInt("seat_id"));
                    return ci;
                }
            }
        }
        return null;
    }

    // 새 Cinema_Infos 레코드를 생성하고, 생성된 cinema_info_id를 반환
    public int createCinemaInfo(int scheduleId, int seatId) throws SQLException {
        String sql = QueryUtil.getQuery("insertCinemaInfo"); // queries.xml에 정의
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, scheduleId);
            pstmt.setInt(2, seatId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("CinemaInfo 생성에 실패하였습니다.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // 생성된 cinema_info_id 반환
                } else {
                    throw new SQLException("CinemaInfo 생성 후 ID를 가져올 수 없습니다.");
                }
            }
        }
    }
}
