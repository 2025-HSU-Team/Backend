package com.sosaw.sosaw.domain.customsound.web.controller;

import com.sosaw.sosaw.domain.customsound.service.CustomSoundService;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.global.response.SuccessResponse;
import com.sosaw.sosaw.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sound")
public class CustomSoundController {
    private final CustomSoundService customSoundService;

    // 사용자 소리 추가
    @PostMapping("/upload")
    public ResponseEntity<SuccessResponse<?>> upload(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute @Valid SoundUploadReq req){
        customSoundService.upload(req, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}

