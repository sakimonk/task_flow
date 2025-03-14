package com.tf.task.flow.common.error;

/**
 * @author ouweijian
 * @date 2024/11/7 1:54
 */
public enum ModuleCode {

    /**
     * TODO_MODULE
     */
    TODO_API(1),
    /**
     * User_MODULE
     */
    USER_API(2),
    ;

    int moduleCode;

    ModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
    }

    public int getModuleCode() {
        return moduleCode;
    }
}
