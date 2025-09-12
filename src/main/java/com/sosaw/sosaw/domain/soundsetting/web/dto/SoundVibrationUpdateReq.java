package com.sosaw.sosaw.domain.soundsetting.web.dto;

import com.sosaw.sosaw.domain.soundsetting.entity.enums.SoundKind;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoundVibrationUpdateReq {

    // 커스텀인지 기본 소리인지 구분
    @NotNull(message = "사운드 타입은 필수입니다.")
    private SoundKind soundKind;

    // 사운드 PK (custom_id & default_id)
    @NotNull(message = "사운드 ID는 필수입니다.")
    private Long soundId;

    // 변경할 진동 종류 (1~5)
    @Min(value = 1, message = "진동 값은 1 이상이어야 합니다.")
    @Max(value = 5, message = "진동 값은 5 이하여야 합니다.")
    private int vibrationType;
}
