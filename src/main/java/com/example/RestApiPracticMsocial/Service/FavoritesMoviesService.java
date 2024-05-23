package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.FavoritesMoviesDTO;
import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Repository.FavoritesMoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoritesMoviesService {
    private FavoritesMoviesRepository favoritesMoviesRepository;
    private MoviesService moviesService;
    private UsersService usersService;

    public FavoritesMovies create(FavoritesMoviesDTO dto){
        FavoritesMovies favoritesMovies = FavoritesMovies.builder().user(usersService.readById(dto.getUserId()))
                .movie(moviesService.readById(dto.getMovieId())).build();
        return favoritesMoviesRepository.save(favoritesMovies);
    }

    public List<FavoritesMovies> readAll(){
        return favoritesMoviesRepository.findAll();
    }

    public FavoritesMovies update(FavoritesMovies favoritesMovies){
        return favoritesMoviesRepository.save(favoritesMovies);
    }

    public FavoritesMovies readById(Long id){
        return favoritesMoviesRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы Roles нет значения с таким: "+id));
    }

    public void delete(Long id){
        favoritesMoviesRepository.deleteById(id);
    }
}
