package com.sosaw.sosaw.domain.customsound.web.dto;

import com.sosaw.sosaw.domain.basicsound.entity.enums.BasicSoundType;
import com.sosaw.sosaw.domain.customsound.entity.enums.Color;
import com.sosaw.sosaw.domain.customsound.repository.projection.SoundMatchRow;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;

public record SoundMatchRes(
        String soundName,
        String emoji,
        Color color,
        double similarity,
        boolean alarmEnabled,
        int vibration
) {
    public static SoundMatchRes from(SoundMatchRow row){
        return new SoundMatchRes(
                row.getCustomName(),
                row.getEmoji(),
                row.getColor(),
                row.getSimilarity(),
                row.getAlarmEnabled(),
                row.getVibration()
        );
    }

    public static SoundMatchRes fromBasicSound(BasicSoundType sound, SoundSetting setting) {
        return new SoundMatchRes(
                sound.getLabel(),
                null,
                null,
                -1.0,
                setting.isAlarmEnabled(),
                setting.getVibrationLevel()
        );
    }

    public static SoundMatchRes unknown(){
        return new SoundMatchRes("Unknown", null,null,0.0, false,0);
    }
}
