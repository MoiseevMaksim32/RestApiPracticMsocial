package com.example.RestApiPracticMsocial.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movies")
public class Movies {
    @Id
    private long id;
    @Column(name = "id_omdb_api", nullable = false)
    private long idOmdbApi;
    @Column(name = "title", nullable = false, unique = true, length = 100)
    private String title;
    @Column(name = "poster_path", nullable = true)
    private String posterPath;

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movies movies = (Movies) o;

        return title.equals(movies.title);
    }
}
