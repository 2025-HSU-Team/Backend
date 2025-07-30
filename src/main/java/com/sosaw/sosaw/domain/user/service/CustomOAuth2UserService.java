package com.sosaw.sosaw.domain.user.service;

import com.sosaw.sosaw.domain.user.enums.SocialType;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.domain.user.repository.UserRepository;
import com.sosaw.sosaw.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 카카오에서 사용자 정보 가져오기
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        // 1. 카카오 고유 ID 추출 (long 타입 → string 변환)
        String kakaoId = String.valueOf(attributes.get("id"));

        // 2. kakao_account 객체 꺼내기
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        // 3. 이메일, 닉네임 추출
        String email = (String) kakaoAccount.get("email");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");

        // 4. DB에 해당 소셜 ID로 가입된 유저가 있는지 확인
        User user = userRepository.findBySocialId(kakaoId)
                .orElseGet(() -> {
                    // 없으면 새로 생성
                    return userRepository.save(User.builder()
                            .email(email)
                            .socialType(SocialType.KAKAO)
                            .socialId(kakaoId)
                            .role("ROLE_USER")
                            .build());
                });

        // 5. JWT 토큰 발급
        String jwt = jwtTokenProvider.createToken(user);

        // 6. 클라이언트로 넘길 속성 구성
        Map<String, Object> customAttributes = new HashMap<>(attributes);
        customAttributes.put("jwt", jwt);
        customAttributes.put("email", email);
        customAttributes.put("nickname", nickname);
        customAttributes.put("socialId", kakaoId);

        // 7. Spring Security 인증용 객체 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                customAttributes,
                "socialId" // getName()으로 쓸 식별자 key (Spring 내부용)
        );
    }
}