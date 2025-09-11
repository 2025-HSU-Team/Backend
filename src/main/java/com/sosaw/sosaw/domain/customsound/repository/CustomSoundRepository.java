package com.sosaw.sosaw.domain.customsound.repository;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
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
}
