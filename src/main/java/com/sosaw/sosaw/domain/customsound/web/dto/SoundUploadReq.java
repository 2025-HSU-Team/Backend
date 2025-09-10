package com.sosaw.sosaw.domain.customsound.web.dto;

import com.sosaw.sosaw.domain.customsound.entity.enums.Color;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SoundUploadReq {
    @NotBlank(message = "커스텀 이름은 필수 입력 값입니다.")
    private String customName;

    @NotBlank(message = "이모지는 필수 입력 값입니다.")
    private String emoji;

    @NotNull(message = "색깔은 필수 값입니다.")
    private Color color;

    @NotNull(message = "파일은 필수입니다.")
    private MultipartFile file;
}
