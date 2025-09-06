package com.sosaw.sosaw.domain.customsound.web.controller;

import com.sosaw.sosaw.domain.customsound.service.CustomSoundService;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sound")
public class CustomSoundController {
    private final CustomSoundService customSoundService;

    // 사용자 소리 추가
    @PostMapping("/upload")
    public ResponseEntity<SuccessResponse<?>> upload(@ModelAttribute @Valid SoundUploadReq req){
        customSoundService.upload(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}

