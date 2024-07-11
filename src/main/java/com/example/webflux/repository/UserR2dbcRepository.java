package com.example.webflux.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {
    // name이라는 column이 where절로 들어감
    Flux<User> findByName(String name);
    Flux<User> findByNameOrderByIdDesc(String name);

    // JPQL 사용
    @Modifying
    @Query("DELETE FROM users WHERE name = :name")
    Mono<Void> deleteByName(String name);
}
