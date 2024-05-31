package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.FavoritesMoviesDTO;
import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Service.FavoritesMoviesService;
import com.example.RestApiPracticMsocial.Service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritesMovies")
@Slf4j
@AllArgsConstructor
public class FavoritesMoviesController {

    private FavoritesMoviesService service;

    @PostMapping
    public ResponseEntity<FavoritesMovies> create(@RequestParam("User-Id") Long id, @RequestBody FavoritesMoviesDTO dto) {
        return new ResponseEntity<>(service.create(id, dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<FavoritesMovies>> readAll(@RequestParam("User-Id") Long id, @RequestParam("page")Integer page, @RequestParam(name = "page_size", defaultValue = "15", required = false)Integer page_size) {
        return new ResponseEntity<>(service.readAll(id,page,page_size), HttpStatus.OK);
    }

    // Про изменение записей вроде ничего не написано, но пусть будет
    @PutMapping
    public ResponseEntity<FavoritesMovies> update(@RequestBody FavoritesMovies favoritesMovies) {
        return new ResponseEntity<>(service.update(favoritesMovies), HttpStatus.OK);
    }

    @DeleteMapping()
    public HttpStatus delete(@RequestParam("User-Id") Long id, @RequestParam("favorites_id") Long favorites_id) {
        FavoritesMovies favoritesMovies = service.readById(favorites_id);
        if (favoritesMovies.getUser().getId() == id) {
            service.delete(favorites_id);
            return HttpStatus.OK;
        } else {
            throw new RuntimeException("Не пытайтесь удалять избранные фильмы других пользователей");
        }
    }
}
