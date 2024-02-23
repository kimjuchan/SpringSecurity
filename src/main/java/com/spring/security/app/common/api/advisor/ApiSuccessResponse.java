package com.spring.security.app.common.api.advisor;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Getter
@Setter
@Builder
public class ApiSuccessResponse<T> {
    public static final String STATUS_SUCCESS = "SUCCESS";

    private String status;

    private T data;

    public static <T> ApiSuccessResponse<T> create(T data) {
        return ApiSuccessResponse.create(STATUS_SUCCESS, data);
    }

    public static ApiSuccessResponse<Void> create() {
        return ApiSuccessResponse.create(STATUS_SUCCESS, null);
    }

    public static URI createdLocation(Object createdId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdId)
                .toUri();
    }

    private static <T> ApiSuccessResponse<T> create(String status, T data) {
        return ApiSuccessResponse.<T>builder()
                .status(status)
                .data(data)
                .build();
    }
}
