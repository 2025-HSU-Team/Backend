package com.sosaw.sosaw.domain.customsound.web.dto;

import com.sosaw.sosaw.domain.customsound.entity.enums.Color;

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
}
