package org.movieproject.view;

import org.movieproject.model.Movies;
import org.movieproject.model.Schedules;
import org.movieproject.model.Users;
import org.movieproject.service.MovieService;
import org.movieproject.service.UsersService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MovieView {
    private final UsersService usersService;
    private final Scanner scanner;
    private final Connection connection;
    private final MovieService movieService;

    public MovieView(Connection connection) {
        this.usersService = new UsersService(connection);
        this.movieService = new MovieService(connection);
        this.scanner = new Scanner(System.in);
        this.connection = connection;
    }

    public void showMenu(Users loginUser) {
        while (true) {
            System.out.println("===== 사용자 메뉴=====");
            System.out.println("1. 영화 목록 보기");
            System.out.println("0. 로그아웃");
            System.out.print("원하시는 메뉴를 선택해주세요 : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> printAllMovies(); // System.out.println("현재 상영 중인 영화 목록입니다.");
                case 0 ->{
                    System.out.println("로그아웃 되었습니다. 메인 메뉴로 돌아갑니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
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

    private void printAllMovies() {
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
                        System.out.println(schedule.getScheduleId() + "." +  movie.getMovieTitle() + " " +
                                schedule.getStartTime() + " " +
                                movie.getMoviePrice() + "원");
                    } else {
                        System.out.println("영화 ID " + movieId + " 에 해당하는 영화 정보를 찾을 수 없습니다.");
                    }
                }
                System.out.print("원하시는 영화를 선택해주세요 : ");
                int choice = scanner.nextInt();
                scanner.nextLine();
            }
        } catch (SQLException e) {
            System.out.println("영화 목록을 조회하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

}
