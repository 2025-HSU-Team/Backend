package com.sosaw.sosaw.global.integration.fastapi;

import com.sosaw.sosaw.domain.customsound.exception.FastapiCallFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PythonMFCCService {

    private final RestTemplate restTemplate;

    @Value("${fastAPI.base-url}")
    private String fastAPIBaseUrl;

    public List<Double> extractMFCC(Path wavPath) {
        // multipart/form-data 구성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(wavPath.toFile()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        // FastAPI 요청
        try {
            String url = fastAPIBaseUrl + "/extract-mfcc";
            ResponseEntity<List> response = restTemplate.postForEntity(url, request, List.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new FastapiCallFailedException();
            }

            @SuppressWarnings("unchecked")
            List<Double> mfcc = (List<Double>) response.getBody();
            return mfcc;

        }catch (ResourceAccessException e) {
            throw new FastapiCallFailedException();
        }
    }
}

