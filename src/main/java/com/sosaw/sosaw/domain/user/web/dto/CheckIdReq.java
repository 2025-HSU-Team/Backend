package com.sosaw.sosaw.domain.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckIdReq {
    @NotBlank(message = "아이디는 필수입니다.")
    private String id;
}
