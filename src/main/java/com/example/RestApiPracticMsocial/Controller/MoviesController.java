package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Service.FavoritesMoviesService;
import com.example.RestApiPracticMsocial.Service.MoviesService;
import com.example.RestApiPracticMsocial.Service.SelectedNoFavoritesMoviesSecvice;
import com.example.RestApiPracticMsocial.Service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MoviesController {

    private final MoviesService service;
    private final SelectedNoFavoritesMoviesSecvice selectedNoFavoritesMoviesSecvice;

    // Put, Post и Delete убранны т.к о них не уточнялось в условии

    @GetMapping("/All")
    // Если убрать то убудет ошибка неоднозначности, spring считает что методы readAll и getNoMoviesFavoritesUse
    public ResponseEntity<List<Movies>> readAll(@RequestParam(name = "page") Integer page, @RequestParam(name = "sizePage", defaultValue = "15", required = false) Integer size) {
        return new ResponseEntity<>(service.readAll(page, size).getContent(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Movies>> getNoMoviesFavoritesUser(@RequestParam("User-Id") Long id, @RequestParam("loaderType") String loaderType, @RequestParam(name = "page") Integer page, @RequestParam(name = "sizePage", defaultValue = "15", required = false) Integer size) {
        if (loaderType.equals("sql")) {
            return new ResponseEntity<>(selectedNoFavoritesMoviesSecvice.NoMoviesFavoritesUserSQL(id, page, size), HttpStatus.OK);
        } else if (loaderType.equals("inMemory")) {
            return new ResponseEntity<>(selectedNoFavoritesMoviesSecvice.NoMoviesFavoritesUserInMemory(id, page, size), HttpStatus.OK);
        }
        throw new RuntimeException("Что-то пошло не так");
    }
}
