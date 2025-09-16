package com.sosaw.sosaw.domain.soundsetting.service;

import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundAlarmUpdateReq;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundSettingRes;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundVibrationUpdateReq;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.global.security.CustomUserDetails;

import java.util.List;

public interface SoundSettingService {

    // 알림 설정 조회
    List<SoundSettingRes> getAllSoundSetting(User user);

    // 알람 유무 수정
    void updateAlarm(User user, SoundAlarmUpdateReq req);
    void updateCustomAlarm(User user, SoundAlarmUpdateReq req);
    void updateDefaultAlarm(User user, SoundAlarmUpdateReq req);

    // 진동 종류 수정
    void updateVibration(User user, SoundVibrationUpdateReq req);
    void updateCustomVibration(User user, SoundVibrationUpdateReq req);
    void updateDefaultVibration(User user, SoundVibrationUpdateReq req);
}
