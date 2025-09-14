package com.sosaw.sosaw.domain.customsound.service;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.exception.FileProcessFailedException;
import com.sosaw.sosaw.domain.customsound.exception.NotFoundSoundException;
import com.sosaw.sosaw.domain.customsound.exception.UnsupportedExtensionException;
import com.sosaw.sosaw.domain.customsound.port.AudioFeatureExtractor;
import com.sosaw.sosaw.domain.customsound.repository.CustomSoundRepository;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundMatchRes;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundMatchRow;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundsRes;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
import com.sosaw.sosaw.domain.soundsetting.entity.enums.SoundKind;
import com.sosaw.sosaw.domain.soundsetting.repository.SoundSettingRepository;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.domain.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.sosaw.sosaw.domain.user.exception.UserNotFoundException;
import com.sosaw.sosaw.global.jpa.converter.FloatArrayVectorConverter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomSoundServiceImpl implements CustomSoundService{
    private final CustomSoundRepository customSoundRepository;
    private final AudioFeatureExtractor audioFeatureExtractor; // 포트 주입
    private final SoundSettingRepository soundSettingRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void upload(SoundUploadReq req, User user) {
        userRepository.findById(user.getUserId())
                .orElseThrow(UserNotFoundException::new);

        float[] mfcc = audioFeatureExtractor.extractMfcc(req.getFile());
        CustomSound sound = CustomSound.toEntity(user, req, mfcc);

        // 커스텀 소리를 생성할때, SoundSetting 부분도 같이 생기게 함
        SoundSetting.createForCustom(sound);

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
        userRepository.findById(user.getUserId())
                .orElseThrow(UserNotFoundException::new);

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

    @Override
    public SoundMatchRes match(User user, MultipartFile file) {
        userRepository.findById(user.getUserId())
                .orElseThrow(UserNotFoundException::new);

        float[] mfcc = audioFeatureExtractor.extractMfcc(file);

        String literal = FloatArrayVectorConverter.toLiteral(mfcc);

        SoundMatchRow row = customSoundRepository
                .findTopMatchByUserIdWithSimilarity(user.getUserId(), literal)
                .orElseThrow(NotFoundSoundException::new);
        //TODO: 일상생활 소리 탐지와 사용자 소리 어떤 것을 반환할지, 단일 컷 정확도 기준 필요
        // 일상생활 소리 탐지 부분에서 서비스에서 재공되는 9개의 소리가 아닌 다른 소리 분류시 제외 시켜야 함.
        return SoundMatchRes.from(row);
    }

}
