package com.spring.security.app.common.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiSuccessCode {

    OK(HttpStatus.OK, "요청이 완료되었습니다."),
    CREATED(HttpStatus.CREATED, "요청이 완료되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 완료되었습니다.");

    private HttpStatus httpStatus;
    private String message;

    public String toString() {
        return this.httpStatus.value() + " " + this.name();
    }
}
