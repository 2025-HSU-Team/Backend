package com.sosaw.sosaw.domain.customsound.repository;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundMatchRow;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundsRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomSoundRepository extends JpaRepository<CustomSound, Long> {

    // mfcc를 제외하고 필요 컬럼 가져오기 위함.
    @Query("""
    select new com.sosaw.sosaw.domain.customsound.web.dto.SoundsRes(
        c.id, c.customName, c.emoji, c.color)
    from CustomSound c
    where c.user.userId = :userId
    """)
    List<SoundsRes> findAllByUserId(@Param("userId") Long userId);

    // 특정 유저의 커스텀 소리만 조회
    Optional<CustomSound> findByIdAndUserUserId(Long id, Long userId);

    // 유사도가 가장 높은 소리 1가지 추출
    @Query(value = """
        SELECT 
            c.custom_id   AS id,
            c.custom_name AS customName,
            c.emoji       AS emoji,
            c.color       AS color,
            (1 - (c.mfcc <=> (:mfcc)::vector)) AS similarity,
            COALESCE(s.alarm_enabled, false) AS alarmEnabled,
            COALESCE(s.vibration_level, 0)   AS vibration
        FROM "custom_sound" c
        LEFT JOIN sound_setting s ON s.custom_id = c.custom_id
        WHERE c.user_id = :userId
        ORDER BY c.mfcc <=> (:mfcc)::vector ASC
        LIMIT 1
    """, nativeQuery = true)
    Optional<SoundMatchRow> findTopMatchByUserIdWithSimilarity(
            @Param("userId") Long userId,
            @Param("mfcc") String mfccVectorLiteral   // "[0.12, -0.03, ...]" 형태의 문자열
    );
}
