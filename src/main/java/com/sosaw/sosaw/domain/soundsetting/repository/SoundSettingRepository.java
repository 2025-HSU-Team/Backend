package com.sosaw.sosaw.domain.soundsetting.repository;

import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoundSettingRepository extends JpaRepository<SoundSetting, Long> {

    // CustomSound id로 SoundSetting 조회
    Optional<SoundSetting> findByCustomSoundId(Long customId);
}
