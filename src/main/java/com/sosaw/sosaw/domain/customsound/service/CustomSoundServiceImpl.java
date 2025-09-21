package com.sosaw.sosaw.domain.customsound.service;

import com.sosaw.sosaw.domain.basicsound.entity.BasicSound;
import com.sosaw.sosaw.domain.basicsound.entity.enums.BasicSoundType;
import com.sosaw.sosaw.domain.basicsound.exception.NotFoundBasicSoundException;
import com.sosaw.sosaw.domain.basicsound.repository.BasicSoundRepository;
import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.exception.NotFoundCustomSoundException;
import com.sosaw.sosaw.domain.customsound.port.AudioFeatureExtractor;
import com.sosaw.sosaw.domain.customsound.repository.CustomSoundRepository;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundMatchRes;
import com.sosaw.sosaw.domain.customsound.repository.projection.SoundMatchRow;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundsRes;
import com.sosaw.sosaw.domain.soundsetting.entity.SoundSetting;
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
    private final BasicSoundRepository basicSoundRepository;


    @Override
    @Transactional
    public void upload(SoundUploadReq req, User user) {
        userRepository.findById(user.getUserId())
                .orElseThrow(UserNotFoundException::new);

        float[] mfcc = audioFeatureExtractor.extractMfcc(req.getFile());
        CustomSound sound = CustomSound.toEntity(user, req, mfcc);

        // 커스텀 소리를 생성할때, SoundSetting 부분도 같이 생기게 함
        SoundSetting.createForCustom(user,sound);

        customSoundRepository.save(sound);
    }

    @Override
    @Transactional
    public void delete(Long customSoundId) {
        customSoundRepository.findById(customSoundId).ifPresentOrElse(
                customSoundRepository::delete,
                () -> { throw new NotFoundCustomSoundException(); }
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
                .orElseThrow(NotFoundCustomSoundException::new);
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
                .findTopMatchByUserIdWithSimilarity(user.getUserId(), literal);

        if (row == null) {
            log.info("커스텀 소리 매칭 결과 없음");
        } else {
            log.info("커스텀 소리 유사도: {}", row.getSimilarity());
        }

        //1. 커스텀 유사도
        if (row != null && row.getSimilarity() >= 0.997) {
            return SoundMatchRes.from(row);
        }

        //2. tf모델 예측
        String label = audioFeatureExtractor.predict(file);

        log.info("매핑된 사운드 종류 : {}", label);

        BasicSoundType sound = BasicSoundType.fromLabel(label);

        //커스텀 유사도가 낮고 tf에서도 예측불가능
        if (sound == BasicSoundType.UNKNOWN) {
            return SoundMatchRes.unknown();
        }

        BasicSound basicSound = basicSoundRepository.findBySoundType(sound)
                .orElseThrow(NotFoundBasicSoundException::new);

        SoundSetting setting = soundSettingRepository.findByUserUserIdAndBasicSound(user.getUserId(), basicSound)
                .orElse(SoundSetting.createForBasic(user, basicSound));

        return SoundMatchRes.fromBasicSound(sound, setting);
    }

}
