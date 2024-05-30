package com.example.RestApiPracticMsocial.DTO;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoviesDTO moviesDTO = (MoviesDTO) o;

        return Objects.equals(title, moviesDTO.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
