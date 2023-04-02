package com.reactivespring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by jonghyeon on 2023/04/02,
 * Package : com.reactivespring.router
 */
@Configuration
public class ReviewRouter {
    @Bean
    public RouterFunction<ServerResponse> reviewsRoute(){
        return route()
                .GET("/v1/helloWorld", request -> ServerResponse.ok().bodyValue("hello world"))
                .build();
    }
}
