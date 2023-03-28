package com.reactiviespring.repository;

import com.reactiviespring.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by jonghyeon on 2023/03/21,
 * Package : com.reactiviespring.repository
 */
public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {
}
