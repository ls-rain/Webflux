package com.example.webflux.controller;

import com.example.webflux.dto.UserCreateRequest;
import com.example.webflux.dto.UserPostResponse;
import com.example.webflux.dto.UserResponse;
import com.example.webflux.dto.UserUpdateRequest;
import com.example.webflux.service.PostService;
import com.example.webflux.service.PostServiceV2;
import com.example.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PostServiceV2 postServiceV2;
    @PostMapping("")
    public Mono<UserResponse> createUser(@RequestBody UserCreateRequest userCreateRequest){
        return userService.create(userCreateRequest.getName(), userCreateRequest.getEmail())
                .map(UserResponse::of);
    }

    @GetMapping("")
    public Flux<UserResponse> findAllUsers(){
        return userService.findAll()
                .map(UserResponse::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> findUser(@PathVariable Long id){
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deleteUser(@PathVariable Long id){
        //정상응답 205가 아니라 204 (no content) 응답으로 주고 싶다.
        return userService.deleteById(id).then(
                Mono.just(ResponseEntity.noContent().build())
        );
    }
    @DeleteMapping("/search")
    public Mono<ResponseEntity<?>> deleteUser(@RequestParam String name){
        //정상응답 205가 아니라 204 (no content) 응답으로 주고 싶다.
        return userService.deleteByName(name).then(
                Mono.just(ResponseEntity.noContent().build())
        );
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        return userService.update(id, request.getName(), request.getEmail())
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/{id}/posts")
    public Flux<UserPostResponse> getUserPosts(@PathVariable Long id){
        return postServiceV2.findAllByUserId(id)
                .map(UserPostResponse::of);
    }
}
