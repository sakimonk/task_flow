package com.tf.task.flow.common.domain;

import com.tf.task.flow.common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Result Class
 * @author ouweijian
 * @date 2025/3/11 21:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    /**
     * Response Code
     * */
    private int code;

    /**
     * Response Message
     * */
    private String message;

    /**
     * Response Data
     * */
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "Success", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "Success", null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> error(ErrorCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> Result<T> error(ErrorCode resultCode, String message) {
        return new Result<>(resultCode.getCode(), message, null);
    }

    public static <T> Result<T> of(int code, String message, T data) {
        return new Result<>(code, message, data);
    }
}
