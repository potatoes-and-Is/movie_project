package org.movieproject.view;

import org.movieproject.model.Tickets;
import org.movieproject.service.ReservationsService;
import org.movieproject.service.TicketsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TicketsView {

    private final TicketsService ticketsService;
    private final ReservationsService reservationsService;
    private final Scanner scanner;

    public TicketsView(Connection connection) {
        this.ticketsService = new TicketsService(connection);
        this.reservationsService = new ReservationsService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void cancelTicketList(int userId) throws SQLException {

        try {
            // 예매 내역 리스트
            List<Tickets> tickets = ticketsService.getTicketsByUserId(userId);

            if (tickets.isEmpty()) {
                System.out.println("예매 내역이 없습니다.");
            } else {
                System.out.println("\n===== 예매 내역 =====");
                int num = 1;
                for (Tickets ticket : tickets) {
                    System.out.println(num++ + ". " + ticket.getTicketId());
                }

                System.out.print("취소할 티켓 번호 : ");
                int ticketIndex = 0;
                boolean flag = true;
                while (flag) {
                    ticketIndex = scanner.nextInt();
                    // 인덱스 오류 잡기
                    if (ticketIndex > 0 && ticketIndex <= tickets.size()) {
                        flag = false;
                    } else {
                        System.out.println("취소할 예매 번호가 없습니다. 다시 입력해주세요.");
                    }
                }

                // 예매 내역 취소하기
                ticketsService.cancelTicket(tickets.get(ticketIndex - 1));
                // 예약 정보 삭제하기
                reservationsService.deleteReservation(tickets.get(ticketIndex - 1).getReservationId());
                System.out.println("예매가 취소되었습니다.");
            }
        } catch (SQLException e) {
            System.out.println("예매 내역을 조회하는 중 오류가 발생했습니다.");
        }
    }
}
