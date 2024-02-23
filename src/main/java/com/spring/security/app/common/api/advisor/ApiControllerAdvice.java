package com.spring.security.app.common.api.advisor;

import com.spring.security.app.common.api.dto.ApiErrorResponse;
import com.spring.security.app.common.api.enums.ApiErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    private ApiErrorResponse apiErrorResponse;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exceptionHandler(Exception e){
        ApiErrorCode apiErrorCode = ApiErrorCode.INTERNAL_SERVER_ERROR;
        apiErrorResponse.create(apiErrorCode.getHttpStatus(), apiErrorCode.getMessage(), new Date());

        log.error("{} : {}", apiErrorResponse.getStatus(), apiErrorResponse.getErrorMessgae());
        return ResponseEntity.status(apiErrorCode.getHttpStatus()).body(apiErrorResponse);
    }

    @ExceptionHandler(value = {
            InsufficientAuthenticationException.class,
            BadCredentialsException.class,
            //InvalidBearerTokenException.class,
            //OAuth2AuthenticationException.class
            AccessDeniedException.class,
    })
    public ResponseEntity<ApiErrorResponse> authenticationExceptionHandler(Exception e) {
        ApiErrorCode apiErrorCode = ApiErrorCode.UNAUTHORIZED;
        apiErrorResponse.create(apiErrorCode.getHttpStatus(), apiErrorCode.getMessage(), new Date());

        log.error("{} : {}", apiErrorCode.name(), e.getMessage(), e);
        return ResponseEntity.status(apiErrorCode.getHttpStatus()).body(apiErrorResponse);
    }

    //요청 경로에 대응하는 컨트롤러 메소드 x
    //RequestMapping 잘못 구성
    //DispatcherServlet이 적절한 핸들러 찾지 못한 경우.
    @ExceptionHandler(value = {NoHandlerFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> notFoundExceptionHandler(final Exception e) {
        ApiErrorCode apiErrorCode = ApiErrorCode.NOT_FOUND;
        apiErrorResponse.create(apiErrorCode.getHttpStatus(), apiErrorCode.getMessage(), new Date());

        log.error("{} : {}", apiErrorCode.name(), e.getMessage(), e);
        return ResponseEntity.status(apiErrorCode.getHttpStatus()).body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> validExceptionHandler(final MethodArgumentNotValidException e) {;
        BindingResult bindingResult = e.getBindingResult();
        Optional<FieldError> error = Optional.of(bindingResult.getFieldError());
        ApiErrorCode apiErrorCode = ApiErrorCode.BAD_REQUEST;
        //이게 맞나 ??
        apiErrorResponse.create(apiErrorCode.getHttpStatus(),error.isPresent() ? error.get().getDefaultMessage() : "예외처리 도중 NullPointerException....  ", new Date());
        log.error("{} : {}", apiErrorCode.name(), e.getMessage(), e);
        return ResponseEntity.status(apiErrorCode.getHttpStatus()).body(apiErrorResponse);
    }

}
