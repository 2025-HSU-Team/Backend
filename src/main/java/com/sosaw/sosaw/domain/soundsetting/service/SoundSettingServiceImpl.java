package com.sosaw.sosaw.domain.soundsetting.service;

import com.sosaw.sosaw.domain.basicsound.entity.BasicSound;
import com.sosaw.sosaw.domain.basicsound.repository.BasicSoundRepository;
import com.sosaw.sosaw.domain.customsound.exception.NotFoundSoundException;
import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.repository.CustomSoundRepository;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
import com.sosaw.sosaw.domain.soundsetting.repository.SoundSettingRepository;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundAlarmUpdateReq;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundVibrationUpdateReq;
import com.sosaw.sosaw.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SoundSettingServiceImpl implements SoundSettingService {

    private final BasicSoundRepository basicSoundRepository;
    private final CustomSoundRepository customSoundRepository;
    private final SoundSettingRepository soundSettingRepository;

    @Override
    @Transactional
    public void updateAlarm(User user, SoundAlarmUpdateReq req) {
        switch (req.getSoundKind()) {
            case CUSTOM -> updateCustomAlarm(user, req);
            case DEFAULT -> updateDefaultAlarm(user, req); // 기본 사운드 구현 시 작성
        }
    }

    @Override
    @Transactional
    public void updateVibration(User user, SoundVibrationUpdateReq req) {
        switch (req.getSoundKind()) {
            case CUSTOM -> updateCustomVibration(user, req);
            case DEFAULT -> updateDefaultVibration(user, req); // 기본 사운드 구현 시 작성
        }
    }

    @Override
    @Transactional
    public void updateCustomAlarm(User user, SoundAlarmUpdateReq req) {
        // 1) 내 커스텀 사운드인지 검증
        CustomSound customSound = customSoundRepository
                .findByIdAndUserUserId(req.getSoundId(), user.getUserId())
                .orElseThrow(NotFoundSoundException::new);

        // 2) 설정 조회 or 생성
        SoundSetting setting = soundSettingRepository
                .findByCustomSoundId(customSound.getId())
                .orElseGet(() -> SoundSetting.createForCustom(user, customSound));

        // 3) 알람 유무 반영
        setting.changeAlarmEnabled(req.isAlarmEnabled());

        // 4) 저장
        soundSettingRepository.save(setting);
    }

    @Override
    @Transactional
    public void updateCustomVibration(User user, SoundVibrationUpdateReq req) {
        // 1) 내 커스텀 사운드인지 검증
        CustomSound customSound = customSoundRepository
                .findByIdAndUserUserId(req.getSoundId(), user.getUserId())
                .orElseThrow(NotFoundSoundException::new);

        // 2) 설정 조회 or 생성
        SoundSetting setting = soundSettingRepository
                .findByCustomSoundId(customSound.getId())
                .orElseGet(() -> SoundSetting.createForCustom(user, customSound));

        // 3) 진동 종류 반영 (1~5)
        setting.changeVibrationType(req.getVibrationType());

        // 4) 저장
        soundSettingRepository.save(setting);
    }

    @Override
    public void updateDefaultAlarm(User user, SoundAlarmUpdateReq req) {
        // 1) 기본 사운드 검증
        BasicSound basicSound = basicSoundRepository.findById(req.getSoundId())
                .orElseThrow(NotFoundSoundException::new);

        // 2) 설정 조회 or 생성
        SoundSetting setting = soundSettingRepository
                .findByUserUserIdAndBasicSoundId(user.getUserId(), basicSound.getId())
                .orElseGet(() -> SoundSetting.createForBasic(user, basicSound));

        // 3) 알람 반영
        setting.changeAlarmEnabled(req.isAlarmEnabled());

        // 4) 저장
        soundSettingRepository.save(setting);
    }

    @Override
    public void updateDefaultVibration(User user, SoundVibrationUpdateReq req) {
        // 1) 기본 사운드 검증
        BasicSound basicSound = basicSoundRepository.findById(req.getSoundId())
                .orElseThrow(NotFoundSoundException::new);

        // 2) 설정 조회 or 생성
        SoundSetting setting = soundSettingRepository
                .findByUserUserIdAndBasicSoundId(user.getUserId(), basicSound.getId())
                .orElseGet(() -> SoundSetting.createForBasic(user, basicSound));

        // 3) 진동 종류 반영 (1~5)
        setting.changeVibrationType(req.getVibrationType());

        // 4) 저장
        soundSettingRepository.save(setting);
    }

}
