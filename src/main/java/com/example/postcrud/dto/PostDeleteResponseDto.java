package com.example.postcrud.dto;

import lombok.Getter;

@Getter
public class PostDeleteResponseDto {
    private String message;
    private int status;

    public PostDeleteResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
