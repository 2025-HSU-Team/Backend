package com.sosaw.sosaw.domain.soundsetting.service;

import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundAlarmUpdateReq;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundVibrationUpdateReq;
import com.sosaw.sosaw.domain.user.entity.User;

public interface SoundSettingService {

    // 알람 유무 수정
    void updateAlarm(User user, SoundAlarmUpdateReq req);
    void updateCustomAlarm(User user, SoundAlarmUpdateReq req);
    void updateDefaultAlarm(User user, SoundAlarmUpdateReq req);

    // 진동 종류 수정
    void updateVibration(User user, SoundVibrationUpdateReq req);
    void updateCustomVibration(User user, SoundVibrationUpdateReq req);
    void updateDefaultVibration(User user, SoundVibrationUpdateReq req);
}
