package com.sosaw.sosaw.domain.customsound.service;

import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;

public interface CustomSoundService {
    void upload(SoundUploadReq req);
}
