package com.sosaw.sosaw.domain.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpReq {

    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^(?=.{4,12}$)(?=.*[a-z])(?=.*\\d)[a-z\\d]+$", message = "아이디는 영문 소문자와 숫자의 조합으로 4~12자 이내여야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.{4,12}$)(?=.*[a-z])(?=.*\\d)[a-z\\d]+$", message = "비밀번호는 영문 소문자와 숫자의 조합으로 4~12자 이내여야 합니다.")
    private String password;
}
