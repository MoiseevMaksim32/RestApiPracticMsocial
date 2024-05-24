package com.example.RestApiPracticMsocial.DTO;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class MoviesDTO {
    @JsonIgnore
    private int idMovieOmdbapi;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Poster")
    private String poster;

    public MoviesDTO(String title,String poster){
        this.title = title;
        this.poster = poster;
    }
    public MoviesDTO(){
    }
}
