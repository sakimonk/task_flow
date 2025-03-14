package com.tf.task.flow.common.error;

/**
 * @author ouweijian
 * @date 2024/11/8 17:34
 */
public class SystemException extends RuntimeException {

    private int code;  // 错误码
    private String message;  // 错误信息

    public SystemException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public SystemException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.code = errorCode.getCode();
        this.message = message;
    }

    // getter 和 setter
    public int getCode() {
        return code;
    }


    @Override
    public String getMessage() {
        return message;
    }

}
