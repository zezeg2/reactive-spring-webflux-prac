package com.reactiviespring.controller;

import com.reactiviespring.domain.MovieInfo;
import com.reactiviespring.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class MovieInfoController {

    private final MovieInfoService movieInfoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@Validated @RequestBody MovieInfo movieInfo){
        return movieInfoService.addMovieInfo(movieInfo).log();
    }

    @GetMapping
    public Flux<MovieInfo> getAllMovieInfo(){
        return movieInfoService.getAllMovieInfo().log();
    }

    @GetMapping("/{id}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id){
        return movieInfoService.getMovieInfoById(id).log();
    }

    @PutMapping("/{id}")
    public Mono<MovieInfo> updateMovieInfo(@RequestBody MovieInfo movieInfo, @PathVariable String id){
        return movieInfoService.updateMovieInfo(movieInfo, id).log();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id){
        return movieInfoService.deleteMovieInfo(id).log();
    }
}
