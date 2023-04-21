package com.example.postcrud.entity;


import com.example.postcrud.dto.PostCURequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userid;

    public Post(PostCURequestDto requestDto, Long user_id, String username) {
        this.title = requestDto.getTitle();
        this.username = username;
        this.content = requestDto.getContent();
        this.userid = user_id;

    }

    public void update(PostCURequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
