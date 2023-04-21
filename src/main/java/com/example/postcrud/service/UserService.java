package com.example.postcrud.service;

import com.example.postcrud.dto.UserLoginRequestDto;
import com.example.postcrud.dto.UserLoginResponseDto;
import com.example.postcrud.dto.UserSignupRequestDto;
import com.example.postcrud.dto.UserSignupResponseDto;
import com.example.postcrud.entity.User;
import com.example.postcrud.jwt.JwtUtil;
import com.example.postcrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 회원가입 서비스 메서드
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // 아이디 중복 체크
        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());
        // isPresent() : 값이 있으면 true를 반환하고 그렇지 않으면 false를 반환합니다.
        if(user.isPresent()){
            return new UserSignupResponseDto("중복된 아이디가 존재합니다!", HttpStatus.NOT_FOUND.value());
        }

        userRepository.save(new User(requestDto));
        return new UserSignupResponseDto("회원가입에 성공하셨습니다!", HttpStatus.OK.value());
    }


    @Transactional(readOnly = true)
    public UserLoginResponseDto login(UserLoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 아이디 존재 유무 체크
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NullPointerException("아이디가 존재하지 않습니다."));
        // 비밀번호 체크
        if(!user.getPassword().equals(password)){
            return new UserLoginResponseDto("비밀번호가 틀립니다.", HttpStatus.NOT_FOUND.value());
        }

        // username 과 email로 토큰을 생성해서 Authorization_Header와 함께 response에 HEADER를 날린다.
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getEmail()));
        return new UserLoginResponseDto("로그인에 성공하셨습니다!", HttpStatus.OK.value());
    }
}
