package com.example.postcrud.service;


import com.example.postcrud.dto.PostRequestDto;
import com.example.postcrud.dto.PostResponseDto;
import com.example.postcrud.entity.Post;
import com.example.postcrud.entity.User;
import com.example.postcrud.jwt.JwtUtil;
import com.example.postcrud.repository.PostRepository;
import com.example.postcrud.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    // 전체 게시글 목록 조회 API
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
//        // Client가 저장하고 있는 토큰을 가져옴
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//        if(token != null) { // 토큰이 비어있지 않다면
//            if (jwtUtil.validateToken(token)) { // 토큰의 유효성 검사
//                claims = jwtUtil.getUserInfoFromToken(token); // 토큰으로부터 유저의 정보를 방아옴.
//            } else{
//                throw new IllegalArgumentException("Token Error");
//            }
//
//            // 토큰 값으로 User Entity 초기화
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new NullPointerException("유저 정보가 존재하지 않습니다.")
//            );
//
//
//        }
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }


//    2. 게시글 작성 API
//    - 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
//    - 제목, 작성 내용을 저장하고
//    - 저장된 게시글을 Client 로 반환하기(username은 로그인 된 사용자)
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        // Client가 저장하고 있는 토큰을 가져옴
        String token = jwtUtil.resolveToken(request);

        if (token != null) { // 토큰이 비어있지 않다면
            if (jwtUtil.validateToken(token)) { // 토큰의 유효성 검사
                Post post = new Post(requestDto);
                postRepository.save(post);
                return new PostResponseDto(post);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
        }
        return new PostResponseDto(post);
    }

    @Transactional
    public int deletePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (post.getPassword().equals(requestDto.getPassword())) {
            postRepository.deleteById(id);
            return HttpStatus.OK.value();
        }
        return HttpStatus.NOT_FOUND.value();
    }
}
