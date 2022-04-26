package com.meta.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xiong Mao
 * @date 2022/01/25 18:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T> {

    private static final int SUCCESS_CODE = 0;
    private static final int FAILED_CODE = 1;
    private static final String SUCCESS_MSG = "SUCCESS";

    /**
     * 数据
     */
    private T data;
    /**
     * code
     */
    private int code;
    /**
     * 消息
     */
    private String message;
    /**
     * 错误响应时，返回true, 反之则false
     */
    private boolean result;

    /**
     * 适用于成功时返回业务数据
     *
     * @param data
     * @return
     */
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(data, SUCCESS_CODE, SUCCESS_MSG, false);
    }

    public static <T> ResultVo<T> success() {
        return new ResultVo<>(SUCCESS_CODE, SUCCESS_MSG, false);
    }

    /**
     * 适用于发生错误和异常
     *
     * @param message
     * @return
     */
    public static <T> ResultVo<T> fail(String message) {
        return new ResultVo<>(null, FAILED_CODE, message, true);
    }

    private ResultVo(int code, String message, boolean result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
