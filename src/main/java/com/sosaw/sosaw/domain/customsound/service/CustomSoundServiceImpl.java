package com.sosaw.sosaw.domain.customsound.service;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.exception.FileProcessFailedException;
import com.sosaw.sosaw.domain.customsound.exception.NotFoundSoundException;
import com.sosaw.sosaw.domain.customsound.exception.UnsupportedExtensionException;
import com.sosaw.sosaw.domain.customsound.port.AudioFeatureExtractor;
import com.sosaw.sosaw.domain.customsound.repository.CustomSoundRepository;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundsRes;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.global.integration.fastapi.PythonMFCCService;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomSoundServiceImpl implements CustomSoundService{
    private final PythonMFCCService pythonMFCCService;
    private final CustomSoundRepository customSoundRepository;
    private final AudioFeatureExtractor audioFeatureExtractor; // 포트 주입


    @Override
    @Transactional
    public void upload(SoundUploadReq req, User user) {
        float[] mfcc = audioFeatureExtractor.extractMfcc(req.getFile());
        CustomSound sound = CustomSound.toEntity(user, req, mfcc);
        customSoundRepository.save(sound);
    }

    @Override
    @Transactional
    public void delete(Long customSoundId) {
        customSoundRepository.findById(customSoundId).ifPresentOrElse(
                customSoundRepository::delete,
                () -> { throw new NotFoundSoundException(); }
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<SoundsRes> getAllSounds(User user) {
        return customSoundRepository.findAllByUserId(user.getUserId());
    }

    @Override
    @Transactional
    public void modify(SoundUploadReq req, Long customSoundId) {
        CustomSound sound = customSoundRepository.findById(customSoundId)
                .orElseThrow(NotFoundSoundException::new);
        float[] mfcc = audioFeatureExtractor.extractMfcc(req.getFile());
        sound.replace(req, mfcc);
    }

}
