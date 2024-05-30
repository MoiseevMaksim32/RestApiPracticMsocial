package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Repository.MoviesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class MoviesService {
    // На всякий случай была реализована полностью CRUD система
    private final MoviesRepository moviesRepository;

    public Movies create(MoviesDTO dto) {
        long id = 0;
        if (moviesRepository.count() == 0) {
            id = 0;
        } else {
            id = moviesRepository.findByGetLastId();
        }
        Movies movie = Movies.builder().id(id).idOmdbApi(
                        dto.getIdMovieOmdbapi()).
                title(dto.getTitle()).
                posterPath(dto.getPoster()).build();
        log.info("Создана новая запись фильма с id от внешнего api - " + dto.getIdMovieOmdbapi());
        return moviesRepository.save(movie);
    }

    public int getCountUniqueTitle(String str) {
        return moviesRepository.findByGetCountUniqueTitle(str);
    }

    public void createAll(List<MoviesDTO> listDTO) {
        List<Movies> moviesList = new ArrayList<>();
        long id;
        if (moviesRepository.count() == 0) {
            id = 0;
        } else {
            id = moviesRepository.findByGetLastId();
        }
        for (var i : listDTO) {
            Movies movie = Movies.builder().id(id).idOmdbApi(i.getIdMovieOmdbapi()).
                    title(i.getTitle()).
                    posterPath(i.getPoster()).build();
            moviesList.add(movie);
            id++;
        }
        moviesRepository.saveAll(moviesList);
    }

    public Page<Movies> readAll(Integer page, Integer sizePage) {
        log.info("Получение запрошенных фильмов(страница навигации " + page + ")");
        return moviesRepository.findAll(PageRequest.of(page, sizePage));
    }

    public List<Movies> readAllNoPage() {
        return moviesRepository.findAll();
    }

    public Movies update(Movies movies) {
        return moviesRepository.save(movies);
    }

    public Movies readById(Long id) {
        return moviesRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы фильмов нет значения с таким: " + id));
    }

    public void delete(Long id) {
        moviesRepository.deleteById(id);
    }

    public Integer getLastIdOmdb() {
        return moviesRepository.findByGetLastIdOmdb();
    }

    public Long getCount() {
        return moviesRepository.count();
    }
}
