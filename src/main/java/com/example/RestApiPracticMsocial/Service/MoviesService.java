package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Repository.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MoviesService {
    private final MoviesRepository moviesRepository;
    public Movies create(MoviesDTO dto){
        Movies movie = Movies.builder().title(dto.getTitle()).posterPath(dto.getPosterPath()).build();
        return moviesRepository.save(movie);
    }

    public List<Movies> readAll(){
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
}
