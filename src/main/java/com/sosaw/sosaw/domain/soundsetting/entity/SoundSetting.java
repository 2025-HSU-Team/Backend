package com.sosaw.sosaw.domain.soundsetting.entity;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.soundsetting.entity.enums.SoundKind;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundAlarmUpdateReq;
import com.sosaw.sosaw.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sound_setting")
public class SoundSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="setting_id")
    private Long id;

    // 알람 유무
    @Column(name = "alarm_enabled", nullable = false)
    private boolean alarmEnabled=false;

    // 진동 종류
    @Column(name = "vibration_level", nullable = false)
    private int vibrationLevel;

    // 커스텀 소리
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id", unique = true)
    private CustomSound customSound;

    // 기본 소리 (나중에 추가)


    // 소리 타입 (커스텀 or 기본)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SoundKind soundKind;


    public void setCustomSound(CustomSound customSound) {
        this.customSound = customSound;
    }

    public void changeAlarmEnabled(boolean enabled) {
        this.alarmEnabled = enabled;
    }
}
