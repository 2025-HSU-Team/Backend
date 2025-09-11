package com.sosaw.sosaw.domain.soundsetting.service;

import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundAlarmUpdateReq;
import com.sosaw.sosaw.domain.user.entity.User;

public interface SoundSettingService {
    void updateAlarm(User user, SoundAlarmUpdateReq req);
    void updateCustomAlarm(User user, SoundAlarmUpdateReq req);
    void updateDefaultAlarm(User user, SoundAlarmUpdateReq req);
}
