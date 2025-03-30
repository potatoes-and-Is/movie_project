package org.movieproject.service;

import org.movieproject.dao.ScheduleDao;
import org.movieproject.model.Schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ScheduleService {

    private ScheduleDao scheduleDao;

    public ScheduleService(Connection connection) {
        this.scheduleDao = new ScheduleDao(connection);
    }

    public List<Schedule> getSchedulesByMovieId(int movieId) {
        try {
            return scheduleDao.getSchedulesByMovieId(movieId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
