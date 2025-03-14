package com.tf.task.flow.api.constant;

import com.tf.task.flow.common.error.ErrorCode;
import com.tf.task.flow.common.error.ModuleCode;

/**
 * @author ouweijian
 * @date 2025/3/12 0:08
 */
public enum TodoErrorCode implements ErrorCode {

    TODO_NOT_EXIST(1, "Todo not exist");

    private int code;
    private String message;

    TodoErrorCode(int code, String message) {
        this.code = ModuleCode.TODO_API.getModuleCode() * 1000 + code;
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
