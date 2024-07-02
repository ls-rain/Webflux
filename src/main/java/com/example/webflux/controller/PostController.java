package com.example.webflux.controller;

import com.example.webflux.dto.PostResponse;
import com.example.webflux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public Mono<PostResponse> getPostContent(@PathVariable Long id) {
        return postService.getContent(id);
    }

    @GetMapping("/search")
    public Flux<PostResponse> getMultiplePostContent(@RequestParam("ids")List<Long> idList){
        return postService.getMultiplePostContent(idList);
    }
    @GetMapping("/parallel-search")
    public Flux<PostResponse> getParallelMultiplePostContent(@RequestParam("ids")List<Long> idList){
        return postService.getParallelMultiplePostContent(idList);
    }
}
