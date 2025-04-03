package org.movieproject.service;

import org.movieproject.dao.MyPageDao;
import org.movieproject.model.Tickets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MyPageService {

    private static final Logger log = LoggerFactory.getLogger(MyPageService.class);

    private final MyPageDao myPageDao;
    private final Connection connection;

    /* 생성자 주입 (의존성 주입) */
    public MyPageService(Connection connection) {
        this.myPageDao = new MyPageDao(connection);
        this.connection = connection;
    }

    /* 회원별 티켓 정보 간단히 출력 */
    public List<Tickets> getTicketsByUserId(int userId) throws SQLException {
        return myPageDao.getTicketsByUserId(userId);
    }

    /* 티켓 상세 정보 출력 */
    public Tickets getTicketById(int ticketId) throws SQLException {
        // MyPageDao 인스턴스를 통해 메서드 호출
        Tickets ticketInfo = myPageDao.getTicketById(ticketId);

        if (ticketInfo == null) {
            log.error("티켓 ID가 존재하지 않습니다.");
            return null;
        }
        return ticketInfo;
    }
}