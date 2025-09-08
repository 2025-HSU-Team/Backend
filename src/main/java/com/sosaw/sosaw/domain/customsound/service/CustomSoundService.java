package com.sosaw.sosaw.domain.customsound.service;

import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundsRes;
import com.sosaw.sosaw.domain.user.entity.User;

import java.util.List;

public interface CustomSoundService {
    void upload(SoundUploadReq req, User user);

    void delete(Long customSoundId);

    List<SoundsRes> getAllSounds(User user);
}
