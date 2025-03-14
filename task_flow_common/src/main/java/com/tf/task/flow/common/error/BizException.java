package com.tf.task.flow.common.error;

/**
 * @author ouweijian
 * @date 2024/11/7 1:34
 */
public class BizException extends RuntimeException {

    // err code
    private int code;
    // err msg
    private String message;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
