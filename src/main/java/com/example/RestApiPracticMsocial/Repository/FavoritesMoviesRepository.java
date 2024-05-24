package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesMoviesRepository extends JpaRepository<FavoritesMovies,Long> {

    @Query("Select tab from FavoritesMovies tab where tab.user.id = ?1")
    public List<FavoritesMovies> findAllMoviesUser(Long id);

}
