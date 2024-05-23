package com.example.RestApiPracticMsocial.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "favorites_movies")
public class FavoritesMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name="movies_id", nullable = false)
    private Movies movie;
}
