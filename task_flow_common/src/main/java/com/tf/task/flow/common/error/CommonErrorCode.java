package com.tf.task.flow.common.error;

/**
 * 通用返回码
 * @author ouweijian
 * @date 2024/11/7 14:40
 */
public enum CommonErrorCode implements ErrorCode {

    OK(HttpStatus.OK.value(), "Success"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Parameter Invalid"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "Login Required"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
    ;

    private int code;
    private String message;

    CommonErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
