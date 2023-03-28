package com.reactiviespring.service;

import com.reactiviespring.domain.MovieInfo;
import com.reactiviespring.repository.MovieInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jonghyeon on 2023/03/22,
 * Package : com.reactiviespring.service
 */
@Service
@RequiredArgsConstructor
public class MovieInfoService {
    private final MovieInfoRepository movieInfoRepository;
    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> getAllMovieInfo() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> getMovieInfoById(String id) {
        return movieInfoRepository.findById(id);
    }

    public Mono<MovieInfo> updateMovieInfo(MovieInfo updateMovieInfo, String id) {
        return movieInfoRepository.findById(id)
                .flatMap(movieInfo -> {
                    movieInfo.setCast(updateMovieInfo.getCast());
                    movieInfo.setName(updateMovieInfo.getName());
                    movieInfo.setReleaseDate(updateMovieInfo.getReleaseDate());
                    movieInfo.setYear(updateMovieInfo.getYear());
                    return movieInfoRepository.save(movieInfo);
                });

    }

    public Mono<Void> deleteMovieInfo(String id) {
        return movieInfoRepository.deleteById(id);
    }
}
