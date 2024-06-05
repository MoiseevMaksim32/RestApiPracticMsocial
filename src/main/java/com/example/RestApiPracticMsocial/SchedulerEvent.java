package com.example.RestApiPracticMsocial;

import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.Model.Movies;
import com.example.RestApiPracticMsocial.Service.MoviesService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class SchedulerEvent {

    ObjectMapper mapper = new ObjectMapper();
    final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final EventConfig eventConfig;
    private final MoviesService movieService;

    @Autowired
    public SchedulerEvent(EventConfig eventConfig, MoviesService moviesService) {
        this.eventConfig = eventConfig;
        this.movieService = moviesService;
    }

    /* Т.к API из задания заблокирован в России,
     для примера взял другой Api к
     сожелению в нём нет постарничной навигации
     для примера беру 50 фильмов и сериалов каждые 3 часа */

    @Scheduled(fixedDelayString = "PT03H")
    private void MoviesCollectionEvent() {
        int idMovie;
        List<MoviesDTO> moviesDTOList = new ArrayList<>();
        HashSet<MoviesDTO> hashSetMoviesDTO = new HashSet<>();
        if (movieService.getCount() == 0) {
            idMovie = 1;
        } else {
            idMovie = movieService.getLastIdOmdb() + 1;
        }
        for (; hashSetMoviesDTO.size() != 50; ) {
            String idMovieStr = String.valueOf(idMovie);
            for (int i = idMovieStr.length(); i < 7; i++) {
                idMovieStr = "0" + idMovieStr;
            }
            System.out.println(idMovieStr);
            final HttpUriRequest httpUriRequest = new HttpGet("https://www.omdbapi.com/?apikey=" + eventConfig.getApiKey() + "&i=tt" + idMovieStr);
            try (CloseableHttpResponse response = httpClient.execute(httpUriRequest)) {
                final HttpEntity entity = response.getEntity();
                String jsonString = EntityUtils.toString(entity);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                MoviesDTO moviesDTO = mapper.readValue(jsonString, MoviesDTO.class);
                moviesDTO.setIdMovieOmdbapi(idMovie);
                if (movieService.getCountUniqueTitle(moviesDTO.getTitle()) > 0) {
                    throw new DataIntegrityViolationException("Фильм с именнем - " + moviesDTO.getTitle() + " уже есть в базе");
                }
                hashSetMoviesDTO.add(moviesDTO);
            } catch (ClientProtocolException e) {
                log.error("Ошибка связанная с отправкой запроса запросом: " + e);
            } catch (DataIntegrityViolationException e) {
                log.error("При запросе был получен фильм который уже есть в базе данных: " + e);
            } catch (IOException e) {
                log.error("Ошибка получении фильмов: " + e);
            } finally {
                idMovie++;
            }
        }
        moviesDTOList = hashSetMoviesDTO.stream().toList();
        movieService.createAll(moviesDTOList);
        log.info("Фильмы добавленных успешно");
    }
}
