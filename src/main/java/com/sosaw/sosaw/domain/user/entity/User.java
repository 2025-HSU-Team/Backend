package com.sosaw.sosaw.domain.user.entity;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.user.enums.Role;
import com.sosaw.sosaw.domain.user.enums.SocialType;
import com.sosaw.sosaw.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "app_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String email; // 소셜 로그인용 이메일

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO

    private String socialId; // 플랫폼마다의 고유 ID값

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomSound> customSounds = new ArrayList<>();

    public static User createKakaoUser(String email, String kakaoId) {
        return User.builder()
                .email(email)
                .socialType(SocialType.KAKAO)
                .socialId(kakaoId)
                .role(Role.USER)
                .build();
    }
}