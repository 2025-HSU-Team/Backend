package com.sosaw.sosaw.domain.customsound.web.dto;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.entity.enums.Color;

public record SoundsRes(
        Long SoundId,
        String customName,
        String emoji,
        Color color
) {}
