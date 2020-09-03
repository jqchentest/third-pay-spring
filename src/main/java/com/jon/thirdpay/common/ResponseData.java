package com.jon.thirdpay.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一结果返回类
 */
@Data
public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = -3235859013157368623L;

    private String code = "200";

    private String msg;

    private T data;

    /**
     * Ok rest.
     *
     * @return the rest
     */
    public static ResponseData<String> success() {
        return new ResponseData<>();
    }

    /**
     * Ok rest.
     *
     * @param msg the msg
     * @return the rest
     */
    public static ResponseData<String> success(String code, String msg) {
        ResponseData<String> restBody = new ResponseData<>();
        restBody.setCode(code);
        restBody.setMsg(msg);
        return restBody;
    }

    /**
     * Ok data rest.
     *
     * @param <T> the type parameter
     * @return the rest
     */
    public static <T> ResponseData<T> success(T data) {
        ResponseData<T> restBody = new ResponseData<>();
        restBody.setData(data);
        restBody.setMsg("操作成功");
        return restBody;
    }

    public static <T> ResponseData<T> success(T data, String msg) {
        ResponseData<T> restBody = new ResponseData<>();
        restBody.setData(data);
        restBody.setMsg(msg);
        return restBody;
    }

    /**
     * Build rest.
     *
     * @param <T>  the type parameter
     * @param code the http status
     * @param data the data
     * @param msg  the msg
     * @return the rest
     */
    public static <T> ResponseData<T> build(String code, T data, String msg) {
        ResponseData<T> restBody = new ResponseData<>();
        restBody.setCode(code);
        restBody.setData(data);
        restBody.setMsg(msg);
        return restBody;
    }

    /**
     * Failure rest.
     *
     * @param msg the msg
     * @return the rest
     */
    public static ResponseData<String> failure(String msg) {
        ResponseData<String> restBody = new ResponseData<>();
        restBody.setMsg(msg);
        restBody.setCode("700");
        return restBody;
    }

    /**
     * Failure rest.
     *
     * @param msg the msg
     * @return the rest
     */
    public static <T> ResponseData<T> failure(String code, String msg) {
        ResponseData<T> restBody = new ResponseData<>();
        restBody.setCode(code);
        restBody.setMsg(msg);
        return restBody;
    }

    /**
     * Failure data rest.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @param msg  the msg
     * @return the rest
     */
    public static <T> ResponseData<T> failureData(T data, String msg, String code) {
        ResponseData<T> restBody = new ResponseData<>();
        if (code == null) {
            restBody.setCode("700");
        } else {
            restBody.setCode(code);
        }
        restBody.setData(data);
        restBody.setMsg(msg);
        return restBody;
    }


}
