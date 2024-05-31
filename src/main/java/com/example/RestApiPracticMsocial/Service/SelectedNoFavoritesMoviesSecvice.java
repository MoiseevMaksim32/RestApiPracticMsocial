package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Repository.MoviesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SelectedNoFavoritesMoviesSecvice {
    // Логика поиска была вынесена отдельано, но  sql-запрос на выборку находиться в репозитории фильмов
    private final MoviesService moviesService;
    private final FavoritesMoviesService favoritesMoviesService;
    private final MoviesRepository moviesRepository;

    public Page<Movies> NoMoviesFavoritesUserInMemory(Long id, Integer page, Integer sizePage) {
        Pageable pageable = PageRequest.of(page, sizePage);
        List<FavoritesMovies> favoritesMovies = favoritesMoviesService.readAllNotPage(id);
        List<Movies> movies = moviesService.readAllNoPage();
        List<Movies> resultAll = new ArrayList<>();
        List<Movies> result = new ArrayList<>();
        int startIndex = page * sizePage;
        for (var i : favoritesMovies) {
            for (var j : movies) {
                if (i.getMovie().getId() != j.getId()) resultAll.add(j);
            }
        }
        result = resultAll.subList(startIndex, Math.min(startIndex + sizePage, resultAll.size()));
        log.info("Пользователь с id - " + id + " получил список фильмов которых нет у него в избранных");
        Page<Movies> pageListMovies = new PageImpl<>(result, pageable, resultAll.size());
        return pageListMovies;
    }

    public Page<Movies> NoMoviesFavoritesUserSQL(Long id, Integer page, Integer sizePage) {
        Pageable pageable = PageRequest.of(page, sizePage);
        log.info("Пользователь с id - " + id + " получил список фильмов которых нет у него в избранных");
        return moviesRepository.findAllNoMoviesFavoritesUser(id, pageable);
    }
}
