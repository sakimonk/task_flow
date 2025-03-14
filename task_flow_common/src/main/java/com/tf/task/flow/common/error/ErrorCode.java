package com.tf.task.flow.common.error;

import java.util.Objects;

/**
 * @author ouweijian
 * @date 2024/11/7 3:04
 */
public interface ErrorCode {
    int getCode();
    String getMessage();

    default void assertTrueThrows(boolean v1) throws BizException {
        if (v1) {
            throw new BizException(this);
        }
    }

    /**
     * 断言为 true, 就抛出异常
     *
     * @param v1  断言值
     * @param msg 自定义消息
     * @throws BizException e
     */
    default void assertTrueThrows(boolean v1, String msg) throws BizException {
        if (v1) {
            throw new BizException(this, msg);
        }
    }

    /**
     * 断言值 value 不能为 null, 否则就抛出异常
     *
     * @param value 断言值
     * @param msg   自定义消息
     * @throws BizException e
     */
    default void assertNonNull(Object value, String msg) throws BizException {
        assertTrue(Objects.nonNull(value), msg);
    }

    /**
     * 断言值 value 不能为 null, 否则就抛出异常
     *
     * @param value 断言值
     * @throws BizException e
     */
    default void assertNonNull(Object value) throws BizException {
        assertTrue(Objects.nonNull(value));
    }

    /**
     * 断言必须是 true, 否则抛出异常
     *
     * @param v1 断言值
     * @throws BizException e
     */
    default void assertTrue(boolean v1) throws BizException {
        if (v1) {
            return;
        }

        throw new BizException(this);
    }


    /**
     * 断言必须是 false, 否则抛出异常
     *
     * @param v1 断言值
     * @throws BizException e
     */
    default void assertFalse(boolean v1) throws BizException {
        this.assertTrue(!v1);
    }

    /**
     * 断言必须是 false, 否则抛出异常
     *
     * @param v1  断言值
     * @param msg 自定义消息
     * @throws BizException e
     */
    default void assertFalse(boolean v1, String msg) throws BizException {
        this.assertTrue(!v1, msg);
    }


    /**
     * 断言必须是 true, 否则抛出异常
     *
     * @param v1  断言值
     * @param msg 自定义消息
     * @throws BizException e
     */
    default void assertTrue(boolean v1, String msg) throws BizException {
        if (v1) {
            return;
        }

        throw new BizException(this, msg);
    }

    /**
     * 断言必须是 true, 否则抛出异常
     */
    default BizException toException() throws BizException {
        return new BizException(this, getMessage());
    }
}
