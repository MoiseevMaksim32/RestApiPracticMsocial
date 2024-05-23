package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movies,Long> {
}
