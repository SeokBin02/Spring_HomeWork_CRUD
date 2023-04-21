package com.example.postcrud.entity;

import com.example.postcrud.dto.UserSignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Size(min = 4, max = 10)    // 길이 최소 4자이상 10자 이하
//    @PositiveOrZero             // 양수와 0만 가능
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    // 정규식 표현 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
    @Column(nullable = false, unique = true)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    // 정규식 표현 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    public User(UserSignupRequestDto sign) {
        this.username = sign.getUsername();
        this.password = sign.getPassword();
        this.email = sign.getEmail();
    }
}
