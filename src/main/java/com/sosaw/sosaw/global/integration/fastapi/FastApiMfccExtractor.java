package com.sosaw.sosaw.global.integration.fastapi;

import com.sosaw.sosaw.domain.customsound.exception.FileProcessFailedException;
import com.sosaw.sosaw.domain.customsound.exception.UnsupportedExtensionException;
import com.sosaw.sosaw.domain.customsound.port.AudioFeatureExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FastApiMfccExtractor implements AudioFeatureExtractor {

    private static final int MFCC_DIM = 13;
    private final PythonMFCCService pythonMFCCService;

    @Override
    public float[] extractMfcc(MultipartFile file) {
        Path tmp = saveTempFile(file);
        try {
            List<Double> vec = pythonMFCCService.extractMFCC(tmp);
            return toFloatArray(vec, MFCC_DIM);
        } finally {
            safeDelete(tmp);
        }
    }

    @Override
    public String predict(MultipartFile file) {
        Path tmp = saveTempFile(file);
        try {
            return pythonMFCCService.predict(tmp);
        } finally {
            safeDelete(tmp);
        }
    }

    private Path saveTempFile(MultipartFile file) {
        validateWav(file);
        try {
            byte[] data = file.getBytes();
            Path tmp = Files.createTempFile("wav-", ".wav");
            Files.write(tmp, data);
            return tmp;
        } catch (IOException e) {
            throw new FileProcessFailedException();
        }
    }

    private void validateWav(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new FileProcessFailedException();
        String name = Optional.ofNullable(file.getOriginalFilename()).orElse("upload.wav");
        if (!name.toLowerCase().endsWith(".wav")) throw new UnsupportedExtensionException();
    }

    private float[] toFloatArray(List<Double> src, int dim) {
        if (src == null || src.size() < dim) throw new FileProcessFailedException();
        float[] out = new float[dim];
        for (int i = 0; i < dim; i++) out[i] = src.get(i).floatValue();
        return out;
    }

    private void safeDelete(Path p) {
        if (p == null) return;
        try { Files.deleteIfExists(p); } catch (IOException ignore) {}
    }

}
