package com.sosaw.sosaw.domain.soundsetting.web.controller;

import com.sosaw.sosaw.domain.soundsetting.service.SoundSettingService;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundAlarmUpdateReq;
import com.sosaw.sosaw.domain.soundsetting.web.dto.SoundVibrationUpdateReq;
import com.sosaw.sosaw.global.response.SuccessResponse;
import com.sosaw.sosaw.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sound/setting")
public class SoundSettingController {

    private final SoundSettingService soundSettingService;

    // 알람 유무 수정
    @PutMapping("/alarm")
    public ResponseEntity<SuccessResponse<?>> updateAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid SoundAlarmUpdateReq req
    ) {
        soundSettingService.updateAlarm(userDetails.getUser(), req);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(null));
    }

    // 진동 종류 수정
    @PutMapping("/vibration")
    public ResponseEntity<SuccessResponse<?>> updateVibration(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid SoundVibrationUpdateReq req
    ) {
        soundSettingService.updateVibration(userDetails.getUser(), req);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(null));
    }
}
