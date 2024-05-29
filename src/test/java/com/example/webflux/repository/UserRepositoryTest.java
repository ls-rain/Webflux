package com.example.webflux.repository;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    void save() {
        User user = User.builder().name("uni1").email("uni1@gmail.com").build();
        StepVerifier.create(userRepository.save(user))
                .assertNext(u -> {
                    assertEquals(1L, u.getId());
                    assertEquals("uni1", u.getName());
                    assertEquals("uni1@gmail.com", u.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void findAll() {
        userRepository.save(User.builder().name("uni1").email("uni1@gmail.com").build());
        userRepository.save(User.builder().name("uni2").email("uni2@gmail.com").build());
        userRepository.save(User.builder().name("uni3").email("uni3@gmail.com").build());
        StepVerifier.create(userRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        userRepository.save(User.builder().name("uni1").email("uni1@gmail.com").build());
        userRepository.save(User.builder().name("uni2").email("uni2@gmail.com").build());

        StepVerifier.create(userRepository.findById(2L))
                .assertNext(u -> {
                    assertEquals(2L, u.getId());
                    assertEquals("uni2", u.getName());
                })
                .verifyComplete();
    }

    @Test
    void deleteById() {
        userRepository.save(User.builder().name("uni1").email("uni1@gmail.com").build());
        userRepository.save(User.builder().name("uni2").email("uni2@gmail.com").build());

        StepVerifier.create(userRepository.deleteById(2L))
                .expectNextCount(1)
                .verifyComplete();
    }
}