package com.example.postcrud.controller;


import com.example.postcrud.dto.*;
import com.example.postcrud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 전체 게시글 목록 조회 API
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }


    // 게시글 작성 API
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostCURequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }


    // 선택한 게시글 조회 API
    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }


    // 선택한 게시글 수정 API
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostCURequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }


    // 선택한 게시글 삭제 API
    @DeleteMapping("/posts/{id}")
    public PostDeleteResponseDto deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
}
