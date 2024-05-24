package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Repository.FavoritesMoviesRepository;
import com.example.RestApiPracticMsocial.Repository.MoviesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class MoviesService {
    // На всякий случай была реализована полнось CRUD система
    private final MoviesRepository moviesRepository;

    public Movies create(MoviesDTO dto) {
        Movies movie = Movies.builder().idOmdbApi(dto.getIdMovieOmdbapi()).
                title(dto.getTitle()).
                posterPath(dto.getPoster()).build();
        log.info("Создана новая запись фильма с id от внешнего api - "+ dto.getIdMovieOmdbapi());
        return moviesRepository.save(movie);
    }

    public Page<Movies> readAll(Integer page){
        log.info("Получение запрошенных фильмов(страница навигации "+page+")");
        return moviesRepository.findAll(PageRequest.of(page,15));
    }
    public List<Movies> readAllNoPage(){
        return moviesRepository.findAll();
    }

    public Movies update(Movies movies){
        return moviesRepository.save(movies);
    }

    public Movies readById(Long id){
        return moviesRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы Roles нет значения с таким: "+id));
    }
    public void delete(Long id){
        moviesRepository.deleteById(id);
    }

    public Integer getLastIdOmdb(){
        return moviesRepository.findByGetLastIdOmdb();
    }

    public Long getCount(){
        return moviesRepository.count();
    }

}
