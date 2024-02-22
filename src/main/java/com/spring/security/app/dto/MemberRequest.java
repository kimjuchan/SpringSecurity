package com.spring.security.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
@Setter
public class MemberRequest {

    @NotBlank(message = "loginId 정보없음")
    private String loginId;

    @NotBlank(message = "pwd 정보없음")
    @Size(min=4, max = 20, message = "패스워드는 최소 4자리 이상, 20자리 이하로 입력 해주세요")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 a - z , 숫자 0 -9만 입력 가능")
    private String password;

}
