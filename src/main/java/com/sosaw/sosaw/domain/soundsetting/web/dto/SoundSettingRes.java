package com.sosaw.sosaw.domain.soundsetting.web.dto;

import com.sosaw.sosaw.domain.customsound.entity.enums.Color;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
import com.sosaw.sosaw.domain.soundsetting.entity.enums.SoundKind;

public record SoundSettingRes(
        Long soundId,
        SoundKind soundKind,
        String soundName,
        String emoji,
        Color color,
        boolean alarmEnabled,
        int vibrationLevel

) {
    public static SoundSettingRes from(SoundSetting setting) {
        return new SoundSettingRes(
                setting.getSoundKind() == SoundKind.DEFAULT
                        ? setting.getBasicSound().getId()
                        : setting.getCustomSound().getId(),
                setting.getSoundKind(),
                setting.getSoundKind() == SoundKind.DEFAULT
                        ? setting.getBasicSound().getSoundType().toString()
                        : setting.getCustomSound().getCustomName(),
                setting.getSoundKind() == SoundKind.DEFAULT
                        ? null
                        : setting.getCustomSound().getEmoji(),
                setting.getSoundKind() == SoundKind.DEFAULT
                        ? null
                        : setting.getCustomSound().getColor(),
                setting.isAlarmEnabled(),
                setting.getVibrationLevel()
        );
    }
}
