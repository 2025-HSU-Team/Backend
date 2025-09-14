package com.sosaw.sosaw.domain.soundsetting.entity;

import com.sosaw.sosaw.domain.basicsound.entity.BasicSound;
import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.soundsetting.entity.enums.SoundKind;
import com.sosaw.sosaw.domain.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 알람 유무
    @Column(name = "alarm_enabled", nullable = false)
    private boolean alarmEnabled=true;

    // 진동 종류
    @Column(name = "vibration_level", nullable = false)
    private int vibrationLevel;

    // 커스텀 소리
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id", unique = true)
    private CustomSound customSound;

    // 기본 소리 (나중에 추가)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_id")
    private BasicSound basicSound;

    // 소리 타입 (커스텀 or 기본)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SoundKind soundKind;

    public void setCustomSound(CustomSound customSound) {
        this.customSound = customSound;
        // 양방향 동기화
        if (customSound != null && customSound.getSoundSetting() != this) {
            customSound.setSoundSetting(this);
        }
        this.basicSound = null;
        this.soundKind = SoundKind.CUSTOM;
    }

    public void setBasicSound(BasicSound basicSound) {
        this.basicSound = basicSound;
        this.customSound = null;
        this.soundKind = SoundKind.DEFAULT;
    }

    // 변경 메소드
    public void changeAlarmEnabled(boolean enabled) {
        this.alarmEnabled = enabled;
    }
    public void changeVibrationType(int vibrationLevel) { this.vibrationLevel = vibrationLevel; }

    public static SoundSetting createForCustom(User user, CustomSound customSound) {
        SoundSetting setting = SoundSetting.builder()
                .user(user)
                .alarmEnabled(true)    // 기본값
                .vibrationLevel(1)      // 기본값
                .soundKind(SoundKind.CUSTOM)
                .build();
        setting.setCustomSound(customSound); // 양방향 세팅
        return setting;
    }

    public static SoundSetting createForBasic(User user, BasicSound basicSound) {
        SoundSetting setting = SoundSetting.builder()
                .user(user)
                .alarmEnabled(true)
                .vibrationLevel(1)
                .soundKind(SoundKind.DEFAULT)
                .build();
        setting.setBasicSound(basicSound);
        return setting;
    }
}
