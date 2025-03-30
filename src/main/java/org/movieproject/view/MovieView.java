package org.movieproject.view;

import org.movieproject.model.Movie;
import org.movieproject.service.MovieService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class MovieView {
    private final MovieService movieService;
    private final Scanner scanner;
    private final Connection connection;

    public MovieView(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
        this.movieService = new MovieService(connection);
    }

    /**
     * 영화 목록을 출력하고, 사용자가 올바른 영화 번호를 선택할 때까지 반복하여 입력받은 후 영화 번호를 반환합니다.
     */
    public int displayMovies() {
        while (true) {
            try {
                List<Movie> movies = movieService.getAllMovies();
                if (movies == null || movies.isEmpty()) {
                    System.out.println("현재 영화 목록이 없습니다.");
                    return 0;
                }
                System.out.println("===== 영화 목록 =====");
                for (Movie movie : movies) {
                    System.out.println(movie.getMovieId() + ". " + movie.getTitle() + " (" + movie.getPrice() + "원)");
                }
                System.out.print("영화 번호 선택: ");
                String input = scanner.nextLine().trim();
                int movieId = Integer.parseInt(input);

                // 선택된 번호가 목록에 있는지 확인
                boolean found = false;
                for (Movie movie : movies) {
                    if (movie.getMovieId() == movieId) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    return movieId;
                } else {
                    System.out.println("선택된 영화가 없습니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}
