package com.tf.task.flow.api.constant;

import com.tf.task.flow.common.error.ErrorCode;
import com.tf.task.flow.common.error.ModuleCode;

/**
 * @author ouweijian
 * @date 2025/3/12 12:08
 */
public enum UserErrorCode implements ErrorCode {

    USERNAME_EXIST(1, "The user name already exists"),
    USER_NOT_FOUND(2, "User not found"),
    ;

    private int code;
    private String message;

    UserErrorCode(int code, String message) {
        this.code = ModuleCode.USER_API.getModuleCode() * 1000 + code;
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
