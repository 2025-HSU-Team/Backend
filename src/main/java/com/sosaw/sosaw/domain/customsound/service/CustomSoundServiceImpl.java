package com.sosaw.sosaw.domain.customsound.service;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import com.sosaw.sosaw.domain.customsound.exception.FileProcessFailedException;
import com.sosaw.sosaw.domain.customsound.exception.NotFoundSoundException;
import com.sosaw.sosaw.domain.customsound.exception.UnsupportedExtensionException;
import com.sosaw.sosaw.domain.customsound.repository.CustomSoundRepository;
import com.sosaw.sosaw.domain.customsound.web.dto.SoundUploadReq;
import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.global.integration.fastapi.PythonMFCCService;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public void upload(SoundUploadReq req, User user) {
        MultipartFile file = req.getFile();

        // 1) 검증
        String fileName = Optional.ofNullable(file.getOriginalFilename())
                .orElse("upload.wav");
        if (!fileName.toLowerCase().endsWith(".wav")) {
            throw new UnsupportedExtensionException();
        }

        Path tempDir = null;
        Path saved = null;

        try {
            // 2) 임시 저장
            tempDir = Files.createTempDirectory("wav-");
            saved = tempDir.resolve(fileName);
            req.getFile().transferTo(saved.toFile());

            // 3) MFCC 추출
            List<Double> vector = pythonMFCCService.extractMFCC(saved);

            // 4) DB 저장
            // List<Double> → float[]
            float[] mfcc = new float[13];
            for (int i = 0; i < 13; i++) {
                mfcc[i] = vector.get(i).floatValue();
            }

            CustomSound sound = CustomSound.toEntity(user, req, mfcc);
            customSoundRepository.save(sound);

        } catch (IOException e) {
            throw new FileProcessFailedException();
        } finally {
            // 5) 임시 파일 정리
            safeDelete(saved);
            safeDelete(tempDir);
        }
    }

    @Override
    @Transactional
    public void delete(Long customSoundId) {
        customSoundRepository.findById(customSoundId).ifPresentOrElse(
                customSoundRepository::delete,
                () -> { throw new NotFoundSoundException(); }
        );
    }

    // 임시 파일 삭제 메소드
    private void safeDelete(Path path) {
        if (path != null) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                log.warn("임시 파일 삭제 실패: {}", path, e);
            }
        }
    }
}
