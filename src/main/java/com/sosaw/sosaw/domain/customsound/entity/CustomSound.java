package com.sosaw.sosaw.domain.customsound.entity;

import com.sosaw.sosaw.domain.customsound.entity.enums.Color;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.global.entity.BaseEntity;
import com.sosaw.sosaw.global.jpa.converter.FloatArrayVectorConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Custom_Sound")
public class CustomSound extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="custom_id")
    private Long id;

    @Column(name="custom_name", nullable = false)
    private String customName;

    @Column(nullable = false)
    private String emoji;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "mfcc", columnDefinition = "vector(13)", nullable = false)
    @Convert(converter = FloatArrayVectorConverter.class)
    @JdbcTypeCode(SqlTypes.OTHER)
    private float[] mfcc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 알람, 진동
    @OneToOne(mappedBy = "customSound", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private SoundSetting soundSetting;

    public static CustomSound toEntity(User user, SoundUploadReq req, float[] mfcc){
        return CustomSound.builder()
                .customName(req.getCustomName())
                .emoji(req.getEmoji())
                .color(req.getColor())
                .mfcc(mfcc)
                .user(user)
                .build();
    }

    public void replace(SoundUploadReq req, float[] mfcc){
        this.customName = req.getCustomName();
        this.emoji = req.getEmoji();
        this.color = req.getColor();
        this.mfcc = mfcc;
    }

    public void setSoundSetting(SoundSetting soundSetting) {
        this.soundSetting = soundSetting;
        soundSetting.setCustomSound(this);
    }
}


