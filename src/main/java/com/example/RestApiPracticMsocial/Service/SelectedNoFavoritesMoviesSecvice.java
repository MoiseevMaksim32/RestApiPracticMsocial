package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Repository.MoviesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Movies> NoMoviesFavoritesUserInMemory(Long id){
        List<FavoritesMovies> favoritesMovies = favoritesMoviesService.readAll(id);
        List<Movies> movies = moviesService.readAllNoPage();
        List<Movies> result = new ArrayList<>();
        for(var i: favoritesMovies){
            for(var j: movies){
                if(i.getMovie().getId() != j.getId()) result.add(j);
            }
        }
        log.info("Пользователь с id - "+id+" получил список фильмов кторых нет у него в избранных");
        return result;
    }
    public List<Movies> NoMoviesFavoritesUserSQL(Long id){
        log.info("Пользователь с id - "+id+" получил список фильмов кторых нет у него в избранных");
        return moviesRepository.findAllNoMoviesFavoritesUser(id);
    }
}
