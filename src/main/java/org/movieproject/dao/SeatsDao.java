package org.movieproject.dao;

import com.mysql.cj.protocol.Resultset;
import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;
import org.movieproject.model.Seats;
import org.movieproject.util.QueryUtil;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatsDao {

    private final Connection connection;

    public SeatsDao(Connection connection) {
        this.connection = connection;
    }

    /* 전체 좌석 가져오기 */
    public List<Seats> getAllSeats() {

        List<Seats> seats = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllSeats");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seats.add(new Seats(
                        rs.getInt("seat_id"),
                        rs.getString("seat_number")
                ));
            }
            // System.out.println("모든 좌석 갯수: " + seats.size());
        } catch (SQLException e) {
            System.out.println("getAllSeats: " + e.getMessage());
        }
        return seats;
    }

    /* 해당 상영 시간에 예매 불가능한 좌석 가져오기 */
    public List<Seats> getUnavailSeats(int scheduleId) {

        List<Seats> seats = new ArrayList<>();
        String query = QueryUtil.getQuery("getReservedSeatbyScheduleId");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                seats.add(new Seats(
                        rs.getInt("seat_id"),
                        rs.getString("seat_number")
                ));
            }
            // System.out.println("예약된 좌석 갯수 : " + seats.size());
        } catch (SQLException e) {
            System.out.println("getUnavailSeats: " + e.getMessage());
        }
        return seats;
    }

    /* seatNumber 로 seat 조회 */
    public Seats getSeatBySeatNumber(String seatNumber) {

        Seats seat = null;
        String query = QueryUtil.getQuery("getSeatBySeatNumber");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, seatNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                seat = new Seats(
                        rs.getInt("seat_id"),
                        rs.getString("seat_number")
                );
            }
        } catch (SQLException e) {
            System.out.println("getSeatBySeatNumber: " + e.getMessage());
        }
        return seat;
    }

    /* 좌석 선택 후 cinema_info 테이블 insert */
    public int addCinemaInfo(int scheduleId, int seatId) {

        String query = QueryUtil.getQuery("addCinemaInfo");
        int cinemaInfoId = 0;

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, scheduleId);
            ps.setInt(2, seatId);

            int res = ps.executeUpdate();

            if (res > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    cinemaInfoId =  rs.getInt(1); // 첫 번째 컬럼의 값을 가져옴 : cinema_info_id
                }
            }

        } catch (SQLException e) {
            System.out.println("addCinemaInfo: " + e.getMessage());
        }
        return cinemaInfoId;
    }

    /* cinema_id 로 영화 상영시간과 제목 불러오기 */
    public Movies getCinemaInfoByScheduleId(int scheduleId) {

        Movies movie = null;
        Schedules schedule = null;
        String query = QueryUtil.getQuery("getCinemaInfoByScheduleId");

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                movie = new Movies(
                        rs.getInt("movie_id"),
                        rs.getString("movie_title"),
                        rs.getInt("movie_price")
                );
            }
        }catch (SQLException e) {
            System.out.println("getCinemaInfoByScheduleId: " + e.getMessage());
        }
        return movie;
    }
}