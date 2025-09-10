package com.sosaw.sosaw.domain.customsound.port;

import org.springframework.web.multipart.MultipartFile;

public interface AudioFeatureExtractor {
    float[] extractMfcc(MultipartFile file);
}
