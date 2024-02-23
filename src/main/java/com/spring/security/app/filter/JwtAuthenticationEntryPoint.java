/*
package com.spring.security.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.app.jwt.JwtErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

import java.io.IOException;


@Component
public class JwtAuthenticationEntryPoint  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");

        if(exception == null){
            setResponse(response,JwtErrorCode.TOKEN_NOT_EXIST);
        }
        else if(exception.equals(JwtErrorCode.TOKEN_EXPIRED_ERROR.name())){
            setResponse(response,JwtErrorCode.TOKEN_EXPIRED_ERROR);
        }else if(exception.equals(JwtErrorCode.TOKEN_SIGNATURE_ERROR.name())){
            setResponse(response,JwtErrorCode.TOKEN_SIGNATURE_ERROR);
        }
    }

    private void setResponse(HttpServletResponse response, JwtErrorCode jwtErrorCode) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset-UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ErrorResponse errorResponse = ErrorResponse.of(jwtErrorCode.getHttpStatus(),jwtErrorCode.getCode(),jwtErrorCode.getMessage());
        String result = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(result);
    }

}
*/
