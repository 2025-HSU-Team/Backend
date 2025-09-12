package com.sosaw.sosaw.domain.soundsetting.service;

import com.sosaw.sosaw.domain.customsound.exception.NotFoundSoundException;
import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.repository.CustomSoundRepository;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
import com.sosaw.sosaw.domain.soundsetting.repository.SoundSettingRepository;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundAlarmUpdateReq;
import com.sosaw.sosaw.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoundSettingServiceImpl implements SoundSettingService {

    private final CustomSoundRepository customSoundRepository;
    private final SoundSettingRepository soundSettingRepository;

    @Override
    public void updateAlarm(User user, SoundAlarmUpdateReq req) {
        switch (req.getSoundType()) {
            case CUSTOM -> updateCustomAlarm(user, req);
            case DEFAULT -> updateDefaultAlarm(user, req); // 기본 사운드 구현 시 작성
        }
    }

    @Override
    public void updateCustomAlarm(User user, SoundAlarmUpdateReq req) {
        // 1) 내 커스텀 사운드인지 검증
        CustomSound customSound = customSoundRepository
                .findByIdAndUserUserId(req.getSoundId(), user.getUserId())
                .orElseThrow(NotFoundSoundException::new);

        // 2) 설정 조회 or 생성
        SoundSetting setting = soundSettingRepository
                .findByCustomSoundId(customSound.getId())
                .orElseGet(() -> {
                    SoundSetting s = SoundSetting.builder()
                            .customSound(customSound)
                            .alarmEnabled(false) // 기본값
                            .vibrationLevel(1)   // 기본값
                            .build();
                    customSound.setSoundSetting(s); // 양방향 세팅
                    return s;
                });

        // 3) 알람 유무 반영
        setting.changeAlarmEnabled(req.isAlarmEnabled());

        // 4) 저장
        soundSettingRepository.save(setting);
    }

    @Override
    public void updateDefaultAlarm(User user, SoundAlarmUpdateReq req) {

        throw new UnsupportedOperationException("DEFAULT 사운드는 아직 미구현입니다.");
    }

}
