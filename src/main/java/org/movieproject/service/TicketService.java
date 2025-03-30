package org.movieproject.service;

import org.movieproject.dao.CinemaInfoDao;
import org.movieproject.dao.TicketDao;
import org.movieproject.model.TicketInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private final CinemaInfoDao cinemaInfoDao;
    private final TicketDao ticketDao;


    public TicketService(Connection connection) {
        this.cinemaInfoDao = new CinemaInfoDao(connection);
        this.ticketDao = new TicketDao(connection);
    }

    /**
     * 상영정보(scheduleId)와 좌석(seatId)을 받아 Cinema_Infos에 레코드를 생성한 후,
     * 그 결과를 이용해 Tickets 테이블에 티켓을 생성하고, 생성된 티켓 ID를 반환한다.
     *
     * @param scheduleId 상영 정보 ID
     * @param seatId 좌석 ID
     * @param userId 사용자 ID
     * @return 생성된 티켓 ID
     * @throws SQLException
     */
    public int createTicket(int scheduleId, int seatId, int userId, int paymentId) throws SQLException {
        // Cinema_Infos 테이블에 새 레코드 생성 후 cinema_info_id 반환
        int cinemaInfoId = cinemaInfoDao.createCinemaInfo(scheduleId, seatId);
        // 생성된 cinema_info_id를 사용해 Tickets 테이블에 티켓 생성 및 생성된 ticket_id 반환
        int ticketId = ticketDao.createTicket("N", cinemaInfoId, userId, paymentId);
        return ticketId;
    }

    // 사용자의 예매 내역 반환
    public List<TicketInfo> getTicketInfosByUser(int userId) {
        try {
            return ticketDao.getTicketInfoByUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
