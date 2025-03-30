package org.movieproject.view;

import org.movieproject.model.TicketInfo;
import org.movieproject.service.TicketService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class TicketView {
    private final TicketService ticketService;


    public TicketView(Connection connection) {
        this.ticketService = new TicketService(connection);
    }

    /**
     * 이미 선택된 상영정보와 좌석 정보를 전달받아 티켓을 생성하고, 생성된 티켓 ID를 출력합니다.
     *
     * @param scheduleId 상영정보 ID (예: 이전 UI에서 선택된 상영시간에 해당하는 ID)
     * @param seatId     좌석 ID (예: 이전 UI에서 선택된 좌석의 ID)
     * @param userId     사용자 ID (로그인한 사용자)
     */
    public void createTicket(int scheduleId, int seatId, int userId, int paymentId) {
        try {
            int ticketId = ticketService.createTicket(scheduleId, seatId, userId, paymentId);
            System.out.println("티켓이 생성되었습니다. 티켓 번호: " + ticketId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("티켓 생성에 실패하였습니다.");
        }
    }
}