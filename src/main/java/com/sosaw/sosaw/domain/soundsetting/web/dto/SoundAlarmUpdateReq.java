package com.sosaw.sosaw.domain.soundsetting.web.dto;

import com.sosaw.sosaw.domain.soundsetting.entity.enums.SoundKind;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoundAlarmUpdateReq {

    // 커스텀인지 기본 소리인지 구분
    @NotNull(message = "사운드 타입은 필수입니다.")
    private SoundKind soundKind;

    // 사운드 PK (custom_id & default_id)
    @NotNull(message = "사운드 ID는 필수입니다.")
    private Long soundId;

    // 알람 유무
    private boolean alarmEnabled;

}
