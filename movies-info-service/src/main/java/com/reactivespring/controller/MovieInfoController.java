package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jonghyeon on 2023/03/22,
 * Package : com.reactiviespring.controller
 */
@RestController
@RequestMapping("/api/v1/movie-infos")
@RequiredArgsConstructor
@Slf4j
public class MovieInfoController {

    private final MovieInfoService movieInfoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@Validated @RequestBody MovieInfo movieInfo){
        return movieInfoService.addMovieInfo(movieInfo).log();
    }
    @GetMapping
    public Flux<MovieInfo> getAllMovieInfo(@RequestParam(value = "year", required = false) Integer year){
        if (year != null) {
            log.info("year is : {} ", year);
            return movieInfoService.getMovieInfoFluxByYear(year);
        }
        return movieInfoService.getAllMovieInfo().log();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MovieInfo>> getMovieInfoById(@PathVariable String id){
        return movieInfoService.getMovieInfoById(id).map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())).log();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@RequestBody MovieInfo movieInfo, @PathVariable String id){
        return movieInfoService.updateMovieInfo(movieInfo, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())).log();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id){
        return movieInfoService.deleteMovieInfo(id).log();
    }
}
