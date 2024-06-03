package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Movies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {
    @Query("Select idOmdbApi from Movies ORDER BY idOmdbApi DESC limit 1")
    public Integer findByGetLastIdOmdb();

    @Query("Select id from Movies ORDER BY id DESC limit 1")
    public Long findByGetLastId();

    @Query("Select Count(*) from Movies where title = ?1")
    public Integer findByGetCountUniqueTitle(String str);

    @Query(value = "Select movies.id,movies.id_omdb_api,movies.title,movies.poster_path from movies join favorites_movies on movies.id != favorites_movies.movies_id where favorites_movies.user_id = ?1 ", nativeQuery = true)
    public Page<Movies> findAllNoMoviesFavoritesUser(Long id, Pageable pageable);

}
