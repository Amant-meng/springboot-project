package com.ym.springbootproject.common;

import lombok.Data;

/**
 * 自定义返回结果类
 * @Author yangmeng
 * @date 2021/6/22
 */
@Data
public class ResultBody {

    /** 是否成功 */
    private boolean flag;
    // 成功或失败的值
    private Integer code;
    // 成功提示
    private String message;
    // 返回数据值
    private Object data;
    private Long count;


    private ResultBody() {
    }

    /**
     * 只返回状态，状态码，消息
     * @param flag
     * @param code
     * @param message
     */
    private ResultBody(boolean flag, Integer code, String message) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    /**
     * 只返回状态，状态码，消息，数据对象
     * @param flag
     * @param code
     * @param message
     * @param data
     */
    private ResultBody(boolean flag, Integer code, String message, Object data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回全部消息
     * @param flag
     * @param code
     * @param message
     * @param data
     * @param count
     */
    private ResultBody(boolean flag, Integer code, String message, Object data, Long count) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    /**
     * 返回成功消息
     * @return ResultBody
     */
    public static ResultBody ok() {
        return new ResultBody(true, 200, "成功");
    }

    /**
     * 返回成功消息
     * @return ResultBody
     */
    public static ResultBody ok(Object data) {
        return new ResultBody(true, 200, "成功", data);
    }

    /**
     * 返回成功消息
     * @return ResultBody
     */
    public static ResultBody ok(String message, Object data) {

        return new ResultBody(true, 200, message, data);
    }

    /**
     * 返回成功消息
     * @return ResultBody
     */
    public static ResultBody ok(Object data, Long count) {
        return new ResultBody(true, 200, "成功", data, count);
    }

    /**
     * 返回失败消息
     * @return ResultBody
     */
    public static ResultBody error() {
        return new ResultBody(false,201, "失败");
    }

    /**
     * 返回失败消息
     * @return ResultBody
     */
    public static ResultBody error(String message) {
        return new ResultBody(false, 201, message);
    }

    /**
     * 返回失败消息
     * @return ResultBody
     */
    public static ResultBody error(Integer code, String message) {
        return new ResultBody(false, code, message);
    }


}
