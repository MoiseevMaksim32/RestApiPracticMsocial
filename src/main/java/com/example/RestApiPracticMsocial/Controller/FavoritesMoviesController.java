package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.FavoritesMoviesDTO;
import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Service.FavoritesMoviesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritesMovies")
@AllArgsConstructor
public class FavoritesMoviesController {

    private FavoritesMoviesService service;
    @PostMapping
    public ResponseEntity<FavoritesMovies> create(@RequestBody FavoritesMoviesDTO dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FavoritesMovies>> readAll(){
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FavoritesMovies> readById(@PathVariable Long id){
        return new ResponseEntity<>(service.readById(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FavoritesMovies> update(@RequestBody FavoritesMovies favoritesMovies){
        return new ResponseEntity<>(service.update(favoritesMovies), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        service.delete(id);
        return HttpStatus.OK;
    }
}
