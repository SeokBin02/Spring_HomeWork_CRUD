package com.example.postcrud.dto;

import com.example.postcrud.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String author;
    private String password;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.password = post.getPassword();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
