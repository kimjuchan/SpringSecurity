package com.spring.security.app.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data@Builder
@AllArgsConstructor
public class JwtToken {
    private String refreshToken;
    private String accessToken;
    private String grantType;
}
