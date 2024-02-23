package com.spring.security.app.common.api;


import com.spring.security.app.common.api.enums.ApiErrorCode;
import lombok.Getter;
import lombok.Setter;


/**
 * API 예외처리
 * Default : INTERNAL_SERVER_ERROR
 */
@Getter
public class ApiException extends RuntimeException{

    private final ApiErrorCode apiErrorCode;
    private final String errorMessage;

    public ApiException(){
        super(ApiErrorCode.INTERNAL_SERVER_ERROR.name());

        this.apiErrorCode = ApiErrorCode.INTERNAL_SERVER_ERROR;
        this.errorMessage = ApiErrorCode.INTERNAL_SERVER_ERROR.getMessage();
    }

    public ApiException(ApiErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
        this.errorMessage = apiErrorCode.getMessage();
    }

    public ApiException(String errorMessage) {
        super(ApiErrorCode.INTERNAL_SERVER_ERROR.name());

        this.apiErrorCode = ApiErrorCode.INTERNAL_SERVER_ERROR;
        this.errorMessage = errorMessage;
    }

    public ApiException(ApiErrorCode apiErrorCode, String errorMessage) {
        this.apiErrorCode = apiErrorCode;
        this.errorMessage = errorMessage;
    }
}
