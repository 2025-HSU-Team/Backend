package com.sosaw.sosaw.domain.customsound.entity;

import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
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

    @Column(name = "mfcc", columnDefinition = "vector(13)", nullable = false)
    @Convert(converter = FloatArrayVectorConverter.class)
    @JdbcTypeCode(SqlTypes.OTHER)
    private float[] mfcc;

    public static CustomSound toEntity(SoundUploadReq req, float[] mfcc){
        return CustomSound.builder()
                .customName(req.getCustomName())
                .emoji(req.getEmoji())
                .mfcc(mfcc)
                .build();
    }
}


