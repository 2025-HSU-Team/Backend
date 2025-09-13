package com.sosaw.sosaw.domain.user.service;

import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.domain.user.exception.UserAlreadyExistException;
import com.sosaw.sosaw.domain.user.repository.UserRepository;
import com.sosaw.sosaw.domain.user.web.dto.SignUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
