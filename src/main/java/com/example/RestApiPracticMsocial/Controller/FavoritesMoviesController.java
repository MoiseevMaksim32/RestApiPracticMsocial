package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.FavoritesMoviesDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.JWT.AuthService;
import com.example.RestApiPracticMsocial.Service.FavoritesMoviesService;
import com.example.RestApiPracticMsocial.Service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favoritesMovies")
@Slf4j
@AllArgsConstructor
public class FavoritesMoviesController {

    private FavoritesMoviesService service;
    private UsersService usersService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity<FavoritesMovies> create(@RequestParam("access_token") String token, @RequestBody FavoritesMoviesDTO dto) {
        Users users = usersService.readByLogin(authService.getLogin(token));
        return new ResponseEntity<>(service.create(users.getId(), dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<FavoritesMovies>> readAll(@RequestParam("access_token") String token, @RequestParam("page") Integer page, @RequestParam(name = "page_size", defaultValue = "15", required = false) Integer page_size) {
        Users users = usersService.readByLogin(authService.getLogin(token));
        return new ResponseEntity<>(service.readAll(users.getId(), page, page_size), HttpStatus.OK);
    }

    @DeleteMapping()
    public HttpStatus delete(@RequestParam("access_token") String token, @RequestParam("favorites_id") Long favorites_id) {
        Users users = usersService.readByLogin(authService.getLogin(token));
        FavoritesMovies favoritesMovies = service.readById(favorites_id);
        if (favoritesMovies.getUser().getId() == users.getId()) {
            service.delete(favorites_id);
            return HttpStatus.OK;
        } else {
            throw new RuntimeException("Не пытайтесь удалять избранные фильмы других пользователей");
        }
    }
}
