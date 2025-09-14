package com.sosaw.sosaw.domain.basicsound.entity;

import com.sosaw.sosaw.domain.basicsound.entity.enums.BasicSoundType;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Basic_Sound")
public class BasicSound extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="basic_id")
    private Long id; // pk

    @Enumerated(EnumType.STRING)
    @Column(name = "sound_type", nullable = false, length = 30)
    private BasicSoundType soundType; // 기본음 소리 종류를 구분하기 위함
}
