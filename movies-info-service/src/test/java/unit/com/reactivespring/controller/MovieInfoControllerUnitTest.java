package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


/**
 * Created by jonghyeon on 2023/03/23,
 * Package : com.reactiviespring.controller
 */
@WebFluxTest(controllers = MovieInfoController.class)
@AutoConfigureWebTestClient
public class MovieInfoControllerUnitTest {

    @Autowired
    WebTestClient webTestClient;


    @MockBean
    private MovieInfoService mockMovieInfoService;

    private static String BASE_URL = "/api/v1/movie-infos";

    @Test
    void getAllMovieInfo() {

        var movieInfos = List.of(
                new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));


        when(mockMovieInfoService.getAllMovieInfo()).thenReturn(Flux.fromIterable(movieInfos));
        webTestClient
                .get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    @Test
    void getMovieInfoById() {
        String movieInfoId = "abc";
        when(mockMovieInfoService.getMovieInfoById(movieInfoId)).thenReturn(Mono.just(new MovieInfo(null, "Batman Begins",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"))));
        webTestClient
                .get()
                .uri(BASE_URL + "/{id}", movieInfoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo movieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(movieInfo);
                });
    }

    @Test
    void addMovieInfo() {
        MovieInfo movieInfo = new MovieInfo("mockId", "Batman Begins",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        when(mockMovieInfoService.addMovieInfo(isA(MovieInfo.class))).thenReturn(Mono.just(movieInfo));
        webTestClient
                .post()
                .uri(BASE_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo saved = movieInfoEntityExchangeResult.getResponseBody();
                    assert saved != null;
                    assert saved.getMovieInfoId() != null;
                    assertEquals("mockId", saved.getMovieInfoId());
                    assertEquals("Batman Begins", saved.getName());
                });
    }

    @Test
    void updateMovieInfo() {
        String movieInfoId = "abc";
        MovieInfo movieInfo = new MovieInfo("abc", "바꾸따",
                2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20"));
        when(mockMovieInfoService.updateMovieInfo(isA(MovieInfo.class), isA(String.class))).thenReturn(Mono.just(movieInfo));
        webTestClient
                .put()
                .uri(BASE_URL + "/{id}", movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo updateMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert updateMovieInfo != null;
                    assert updateMovieInfo.getMovieInfoId() != null;
                    assertEquals("바꾸따", updateMovieInfo.getName());
                });
    }

    @Test
    void deleteMovieInfo() {
        String movieInfoId = "abc";

        when(mockMovieInfoService.deleteMovieInfo(movieInfoId)).thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri(BASE_URL + "/{id}", movieInfoId)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
