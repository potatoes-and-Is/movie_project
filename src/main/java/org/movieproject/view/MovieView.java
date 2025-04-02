package org.movieproject.view;

import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;
import org.movieproject.model.Tickets;
import org.movieproject.model.Users;
import org.movieproject.service.MovieService;
import org.movieproject.service.MyPageService;
import org.movieproject.service.TicketsService;
import org.movieproject.service.UsersService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieView {
    private final UsersService usersService;
    private final Scanner scanner;
    private final Connection connection;
    private final MovieService movieService;
    private final MyPageService myPageService;
    private final TicketsService ticketsService;

    // 성리 추가
    private final SeatsView seatsView;

    public MovieView(Connection connection) {
        this.usersService = new UsersService(connection);
        this.movieService = new MovieService(connection);
        this.myPageService = new MyPageService(connection);
        this.ticketsService = new TicketsService(connection);
        this.seatsView = new SeatsView(connection);
        this.scanner = new Scanner(System.in);
        this.connection = connection;
    }

    public void showMenu(Users loginUser) {
        while (true) {
            System.out.println("===== 사용자 메뉴 =====");
            System.out.println("1. 영화 목록 보기");
            System.out.println("2. 예매정보 확인하기");
            System.out.print("원하시는 메뉴를 선택해주세요 : ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> printAllMovies(loginUser); // System.out.println("현재 상영 중인 영화 목록입니다.");
                    case 2 -> showUserTickets(loginUser);
                    default -> System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                scanner.nextLine();
            }
        }
    }

    /* 예매 정보 확인하기 입력 시 회원의 예매 목록 출력 */
    private void showUserTickets(Users loginUser) {
        try {
            int userId = loginUser.getUserId();
            List<Tickets> tickets = myPageService.getTicketsByUserId(userId);

            if (tickets.isEmpty()) {
                System.out.println("예매된 티켓이 없습니다.\n");
                return;
            }

            System.out.println("\n예매 내역");
            for (Tickets ticket : tickets) {
                System.out.println("티켓 ID: " + ticket.getTicketId() +
                        ", 영화 제목: " + ticket.getMovieTitle() +
                        ", 상영 시간: " + ticket.getScheduleStartTime());
                //      ", 영화 제목: " + ticket.cinemaId.scheduleId.movieId.getmovieTitle() +
                //      ", 상영 시간: " + ticket.cinemaId.scheduleId.getScheduleTime());
            }
            checkCancelTicket(tickets);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /* 예매 취소 선택 후 취소할 티켓 ID 입력*/
    private void checkCancelTicket(List<Tickets> tickets) throws SQLException {
        while (true) {
            System.out.println("1. 예매 취소하기");
            System.out.println("2. 뒤로 가기");
            System.out.print("선택: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("\n취소할 티켓 ID를 입력하세요: ");
                    String showDetailTicketId = scanner.nextLine();

                    for (Tickets ticket : tickets) {
                        if (String.valueOf(ticket.getTicketId()).equals(showDetailTicketId)) {
                            showDetailTicket(Integer.parseInt(showDetailTicketId));
                            return;
                        }
                    }
                    System.out.println("티켓 ID를 다시 입력하세요.");
                }
                case "2" -> {
                    System.out.println("뒤로 가기.");
                    return;
                }
                default -> System.out.println("다시 입력하세요.");
            }
        }
    }

    /* 선택한 티켓의 상세정보 출력 */
    public void showDetailTicket(int ticketId) throws SQLException {
        Tickets ticketInfo = myPageService.getTicketById(ticketId);
        System.out.println("\n선택한 티켓 정보");
        if (ticketInfo != null) {
            System.out.println("티켓 ID: " + ticketInfo.getTicketId() +
                    ", 이름: " + ticketInfo.getUserNickname() +
                    ", 영화 제목: " + ticketInfo.getMovieTitle() +
                    ", 상영 시간: " + ticketInfo.getScheduleStartTime() +
                    ", 좌석 번호: " + ticketInfo.getSeatNumber());
        }
        cancelTicket(ticketId);
    }

    /* 예매 취소 진행 */
    public void cancelTicket(int ticketId) throws SQLException {
        while (true) {
            try {
                System.out.print("예매를 취소하시겠습니까? (Y/N): ");
                String cancelChoice = scanner.nextLine().trim().toUpperCase();
                switch (cancelChoice) {
                    case "Y" -> {
                        ticketsService.cancelTicket(ticketId);
                        System.out.println("취소 성공");
                        return;
                    }
                    case "N" -> {
                        System.out.println("사용자 메뉴로 돌아갑니다.\n");
                        return;
                    }
                    default -> {
                        System.out.println("잘못된 입력입니다. 다시 선택해주세요.\n");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }
    // 로그인
    public void loginUser() {
        System.out.print("사용자 닉네임: ");
        String nickname = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        Users loginUser = usersService.login(nickname, password);
        if (loginUser != null) {
            System.out.println("\n로그인 성공! " + loginUser.getUserNickname() + "님 환영합니다!");
            if ("root".equals(loginUser.getUserNickname())) {
                UsersView usersView = new UsersView(connection);
                usersView.showMenu(); // 관리자 메뉴 (여기에도 return 있음)
            } else {
                showMenu(loginUser); // 사용자 메뉴 → 내부에서 로그아웃하면 return
            }
        }
    }

    // 회원가입
    public void signUp(){
        System.out.println("\n===== [회원가입] =====");

        System.out.print("닉네임: ");
        String nickname = scanner.nextLine();

        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        // Users 객체 생성
        Users users = new Users(0, nickname, password, "Y", LocalDateTime.now());

        try {
            boolean success = usersService.registerUser(users); // service → dao로 전달

            if (success) {
                System.out.println("회원가입이 완료되었습니다!");
            } else {
                System.out.println("회원가입에 실패했습니다.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR" + e.getMessage()); // 닉네임 중복 등
        } catch (SQLException e) {
            System.out.println("ERROR 시스템 오류 발생!");
            e.printStackTrace();
        }
    }

    private void printAllMovies(Users loginUser) {
        try {
            List<Schedules> schedules = movieService.getAllMovies();

            if (schedules.isEmpty()) {
                System.out.println("등록된 영화가 없습니다.");
            } else {
                System.out.println("\n===== 전체 영화 목록 =====");
                for (Schedules schedule : schedules) {
                    int movieId = schedule.getMovieId();
                    Movies movie = movieService.getMovieById(movieId); // Movie 객체

                    if (movie != null) {
                        System.out.println(schedule.getScheduleId() + "." +  schedule.getStartTime() + " | " +
                                movie.getMovieTitle() + " | " +
                                movie.getMoviePrice() + "원");
                    } else {
                        System.out.println("영화 ID " + movieId + " 에 해당하는 영화 정보를 찾을 수 없습니다.");
                    }
                }
                System.out.print("관람을 원하시는 영화를 선택해주세요 : ");

                while (true) {
                    try {
                        int scheduleChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (scheduleChoice < 1 || scheduleChoice > 5) {
                            System.out.println("해당 번호의 상영정보가 없습니다. 다시 입력해주세요.");
                            continue;
                        }
                        seatsView.showSeats(scheduleChoice);
                        seatsView.selectSeat(scheduleChoice, loginUser);
                        return;
                    } catch (InputMismatchException e) {
                        System.out.println("잘못된 입력입니다. 관람 원하시는 영화의 번호만 입력해주세요.");
                        scanner.nextLine();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("영화 목록을 조회하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }


}}
