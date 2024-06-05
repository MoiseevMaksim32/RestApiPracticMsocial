package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.JWT.AuthService;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MoviesController {

    private final MoviesService service;
    private final SelectedNoFavoritesMoviesSecvice selectedNoFavoritesMoviesSecvice;
    private final UsersService usersService;
    private final AuthService authService;

    // Put, Post и Delete убранны т.к о них не уточнялось в условии

    @GetMapping("/All")
    // Если убрать то убудет ошибка неоднозначности, spring считает что методы readAll и getNoMoviesFavoritesUse
    public ResponseEntity<List<Movies>> readAll(@RequestParam("access_token") String token, @RequestParam(name = "page") Integer page, @RequestParam(name = "page_size", defaultValue = "15", required = false) Integer size) {
        String login = authService.getLogin(token);
        return new ResponseEntity<>(service.readAll(page, size).getContent(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Movies>> getNoMoviesFavoritesUser(@RequestParam("access_token") String token, @RequestParam("loaderType") String loaderType, @RequestParam(name = "page") Integer page, @RequestParam(name = "page_size", defaultValue = "15", required = false) Integer size) {
        String login = authService.getLogin(token);
        if (loaderType.equals("sql")) {
            return new ResponseEntity<>(selectedNoFavoritesMoviesSecvice.NoMoviesFavoritesUserSQL(usersService.readByLogin(login).getId(), page, size), HttpStatus.OK);
        } else if (loaderType.equals("inMemory")) {
            return new ResponseEntity<>(selectedNoFavoritesMoviesSecvice.NoMoviesFavoritesUserInMemory(usersService.readByLogin(login).getId(), page, size), HttpStatus.OK);
        }
        throw new RuntimeException("Что-то пошло не так");
    }
}
