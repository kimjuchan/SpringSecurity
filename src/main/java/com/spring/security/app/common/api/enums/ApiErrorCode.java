package com.spring.security.app.common.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * API EXCEPTION 처리에 대한 코드 enum 처리
 *
 */
@Getter
@AllArgsConstructor
public enum ApiErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "비정상적인 요청입니다."),

    //지금은 필요 없음.
    //BAD_REQUEST_FILE(HttpStatus.BAD_REQUEST, "비정상적인 첨부파일입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "대상이 존재하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    FORBIDDEN_EXCEED_COUNT(HttpStatus.FORBIDDEN, "접근 권한이 없습니다. 접근 시도 제한 횟수를 초과하였습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 오류가 발생하였습니다.");

    private HttpStatus httpStatus;
    private String message;

    public String toString() {
        return this.name();
    }





}
