package com.sosaw.sosaw.domain.customsound.service;

import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.user.entity.User;

public interface CustomSoundService {
    void upload(SoundUploadReq req, User user);
}
