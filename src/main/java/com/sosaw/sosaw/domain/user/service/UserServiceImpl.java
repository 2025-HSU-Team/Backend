package com.sosaw.sosaw.domain.user.service;

import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.domain.user.exception.PasswordMismatchException;
import com.sosaw.sosaw.domain.user.exception.UserAlreadyExistException;
import com.sosaw.sosaw.domain.user.exception.UserNotFoundException;
import com.sosaw.sosaw.domain.user.repository.UserRepository;
import com.sosaw.sosaw.domain.user.web.dto.SignInReq;
import com.sosaw.sosaw.domain.user.web.dto.SignInRes;
import com.sosaw.sosaw.domain.user.web.dto.SignUpReq;
import com.sosaw.sosaw.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public void signUp(SignUpReq signUpReq) {

        // 아이디 중복 검증
        if(userRepository.existsByLoginId(signUpReq.getId())) {
            throw new UserAlreadyExistException();
        }

        // 비밀번호 인코딩
        String encodedPw = passwordEncoder.encode(signUpReq.getPassword());

        // 팩토리 메서드로 User 생성
        User user = User.createNormalUser(signUpReq.getId(), encodedPw);

        // Repository에 User 저장
        userRepository.save(user);
    }

    // 회원가입시, 아이디 중복 확인
    @Transactional(readOnly = true)
    @Override
    public boolean isLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId); // 중복이면 true, 중복 아니면 false
    }

    // 로그인
    @Transactional(readOnly = true)
    @Override
    public SignInRes signIn(SignInReq signInReq) {
        // 사용자 존재 여부 확인
        User user = userRepository.findByLoginId(signInReq.getId())
                .orElseThrow(UserNotFoundException::new);

        // 비밀번호 검증
        if(!passwordEncoder.matches(signInReq.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException();
        }

        // 토큰 발급
        String token = jwtTokenProvider.createToken(user);

        // 토큰을 응답으로 반환
        return new SignInRes(token);
    }
}
