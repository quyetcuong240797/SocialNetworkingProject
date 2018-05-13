package com.ducnd.model.response;

/**
 * Created by ducnd on 6/26/17.
 */
public class BaseResponse {
    private int statusCode;
    private String msg;
    private Object data;

    public BaseResponse(int statusCode, String msg, Object data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
