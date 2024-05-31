package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.FavoritesMoviesDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Repository.FavoritesMoviesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FavoritesMoviesService {
    // На всякий случай была реализована полностью CRUD система
    private FavoritesMoviesRepository favoritesMoviesRepository;
    private MoviesService moviesService;
    private UsersService usersService;

    public FavoritesMovies create(Long id, FavoritesMoviesDTO dto) {
        FavoritesMovies favoritesMovies = FavoritesMovies.builder().user(usersService.readById(id))
                .movie(moviesService.readById(dto.getMovieId())).build();
        log.info("Новая запись в избранных была создана пользователем с id - " + id + ", он выбрал фильм с id " + dto.getMovieId());
        return favoritesMoviesRepository.save(favoritesMovies);
    }

    public Page<FavoritesMovies> readAll(Long id, Integer page, Integer pageSize) {
        log.info("Пользователь с id - " + id + " запросил список избранных");
        return favoritesMoviesRepository.findAllMoviesUserPage(id, PageRequest.of(page, pageSize));
    }

    public FavoritesMovies update(FavoritesMovies favoritesMovies) {
        return favoritesMoviesRepository.save(favoritesMovies);
    }

    public FavoritesMovies readById(Long id) {
        return favoritesMoviesRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы избранных нет значения с таким id: " + id));
    }

    public void delete(Long id) {
        favoritesMoviesRepository.deleteById(id);
    }

    public List<FavoritesMovies> readAllNotPage(Long id) {
        return favoritesMoviesRepository.findAllMoviesUser(id);
    }
}
