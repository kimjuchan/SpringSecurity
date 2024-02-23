package com.spring.security.app.common.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Client에게 전달할 API Response 폼
 *
 *
 *
 */
@Getter
@Setter
@Builder
//Null 값 허용 안함.
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ApiErrorResponse {

    private HttpStatus status;
    private String errorMessgae;
    private Date errorTimes;

    public ApiErrorResponse(HttpStatus status, String errorMessgae) {
        this.status = status;
        this.errorMessgae = errorMessgae;
    }

    public ApiErrorResponse(String errorMessgae, Date errorTimes) {
        this.errorMessgae = errorMessgae;
        this.errorTimes = errorTimes;
    }

    public ApiErrorResponse(HttpStatus status, String errorMessgae, Date errorTimes) {
        this.status = status;
        this.errorMessgae = errorMessgae;
        this.errorTimes = errorTimes;
    }

    public ApiErrorResponse createErrorReponse(HttpStatus status, String errorMessgae, Date errorTimes){
        return  ApiErrorResponse.builder()
                .status(status)
                .errorMessgae(errorMessgae)
                .errorTimes(errorTimes)
                .build();
    }
}
