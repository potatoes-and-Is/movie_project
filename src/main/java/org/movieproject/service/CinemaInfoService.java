package org.movieproject.service;

import org.movieproject.dao.CinemaInfoDao;
import org.movieproject.model.CinemaInfo;

import java.sql.Connection;
import java.sql.SQLException;

public class CinemaInfoService {

    private CinemaInfoDao cinemaInfoDao;

    public CinemaInfoService(Connection connection) {
        this.cinemaInfoDao = new CinemaInfoDao(connection);
    }

    public CinemaInfo getCinemaInfoById(int cinemaInfoId) {
        try {
            return cinemaInfoDao.getCinemaInfoById(cinemaInfoId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int createCinemaInfo(int scheduleId, int seatId) throws SQLException {
        return cinemaInfoDao.createCinemaInfo(scheduleId, seatId);
    }

}
