package com.sosaw.sosaw.domain.customsound.web.dto;

import com.sosaw.sosaw.domain.customsound.entity.enums.Color;

public interface SoundMatchRow {
    Long getId();
    String getCustomName();
    String getEmoji();
    Color getColor();
    Double getSimilarity(); // 0.0 ~ 1.0 (cosine similarity)
    Boolean getAlarmEnabled();    // alarm은 boolean
    Integer getVibration();
}
