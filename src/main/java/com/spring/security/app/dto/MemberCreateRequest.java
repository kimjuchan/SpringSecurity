package com.spring.security.app.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class MemberCreateRequest {
    /**
     * MemberCreateRequest LOGIN 정보
     * @Param username
     * @Param password
     * @Param email
     * @Valid 어노테이션 참조 : https://jjinhyeok.tistory.com/53
     */

    @NotBlank(message = "loginId 정보없음")
    private String loginId;

    @NotBlank(message = "name 정보없음")
    private String userName;

    @NotBlank(message = "pwd 정보없음")
    @Size(min=4, max = 20, message = "패스워드는 최소 4자리 이상, 20자리 이하로 입력 해주세요")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 a - z , 숫자 0 -9만 입력 가능")
    private String password;

    @NotBlank(message = "email 정보없음")
    @Email(message = "이메일 형식에 맞게 작성 부탁드립니다")
    private String email;

}
