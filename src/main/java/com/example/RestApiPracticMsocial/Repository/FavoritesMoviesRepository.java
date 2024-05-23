package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesMoviesRepository extends JpaRepository<FavoritesMovies,Long> {
}
