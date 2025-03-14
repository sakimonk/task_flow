package com.tf.task.flow.common.error;

import com.tf.task.flow.common.domain.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author ouweijian
 * @date 2025/3/12 1:06
 */
@Slf4j
@RestControllerAdvice
//@RestControllerAdvice(basePackages = "com.tf.task.flow.api.controller")
public class GlobalExceptionHandler {

    // handle validation exception
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<Result<String>> handleBindException(Exception ex) {
        String errorMessage;
        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            errorMessage = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                    .map(x -> x.getField() + x.getDefaultMessage()).collect(Collectors.joining("\n"));
        } else if (ex instanceof BindException bindException) {
            errorMessage = bindException.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else if (ex instanceof ConstraintViolationException constraintViolationException) {
//            errorMessage = constraintViolationException.getConstraintViolations().stream()
//                 .map(constraintViolation -> constraintViolation.getMessage()).collect(Collectors.joining("\n"));
            errorMessage = constraintViolationException.getMessage();
        } else {
            errorMessage = ex.getMessage();
        }

        return ResponseEntity.badRequest().body(Result.error(CommonErrorCode.BAD_REQUEST, errorMessage));
    }

    // Handle service exceptions, such as incorrect user input or insufficient permission
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result<Void>> handleBizException(BizException e) {
        return ResponseEntity.ok(Result.error(e.getCode(), e.getMessage()));
    }

    // Managing system exceptions, usually due to external service failures or unresolvable issues like third-party API errors or network outages.
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Result<Void>> handleSystemException(SystemException e) {
        log.error("handleSystemException", e);
        return ResponseEntity.internalServerError().body(Result.error(e.getCode(), e.getMessage()));
    }

    // Managing unforeseen exceptions beyond business and system issues, often not predicted or caught by the program.
    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public ResponseEntity<Result<Void>> handleAuthException(Exception e) {
        log.error("handleAuthException", e);
        return ResponseEntity.internalServerError().body(Result.error(CommonErrorCode.UNAUTHORIZED, e.getMessage()));
    }
    // Managing unforeseen exceptions beyond business and system issues, often not predicted or caught by the program.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception e) {
        log.error("handleException", e);
        return ResponseEntity.internalServerError().body(Result.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }
}
