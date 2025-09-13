package com.sosaw.sosaw.domain.user.entity;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.user.enums.Role;
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

    @Column(unique = true)
    private String loginId; // 일반 로그인용 아이디

    private String password; // 일반 로그인용 비밀번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomSound> customSounds = new ArrayList<>();

    // 일반 로그인용 빌더
    public static User createNormalUser(String loginId, String encodedPassword) {
        return User.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }
}