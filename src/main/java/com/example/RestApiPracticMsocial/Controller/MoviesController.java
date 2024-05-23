package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Service.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MoviesController {

    private final MoviesService service;
    @PostMapping
    public ResponseEntity<Movies> create(@RequestBody MoviesDTO dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Movies>> readAll(){
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movies> readById(@PathVariable Long id){
        return new ResponseEntity<>(service.readById(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Movies> update(@RequestBody Movies movies){
        return new ResponseEntity<>(service.update(movies), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        service.delete(id);
        return HttpStatus.OK;
    }
}
