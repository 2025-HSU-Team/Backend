package com.sosaw.sosaw.global.integration.fastapi;

import com.sosaw.sosaw.domain.customsound.exception.FastapiCallFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PythonMFCCService {

    private final RestTemplate restTemplate;

    @Value("${fastAPI.base-url}")
    private String fastAPIBaseUrl;

    public List<Double> extractMFCC(Path wavPath) {
        @SuppressWarnings("unchecked")
        List<Double> mfcc = (List<Double>) sendFileToFastApi("/extract-mfcc", wavPath, List.class);
        return mfcc;
    }

    public String predict(Path wavPath) {
        PredictResponse res = sendFileToFastApi("/predict", wavPath, PredictResponse.class);
        log.info("label : {}, prob:{}", res.label, res.prob);
        return res.label();
    }

    private <T> T sendFileToFastApi(String endPoint, Path wavPath, Class<T> responseType) {
        // multipart/form-data 구성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(wavPath.toFile()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            String url = fastAPIBaseUrl + endPoint;
            ResponseEntity<T> response = restTemplate.postForEntity(url, request, responseType);

            log.info("response body: {}", response.getBody());

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new FastapiCallFailedException();
            }

            return response.getBody();

        } catch (ResourceAccessException e) {
            throw new FastapiCallFailedException();
        }
    }

    public record PredictResponse(
        String label,
        double prob
    ){}
}

