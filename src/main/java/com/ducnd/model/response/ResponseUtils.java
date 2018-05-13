package com.ducnd.model.response;

import com.ducnd.Constants;

/**
 * Created by ducnd on 6/26/17.
 */
public class ResponseUtils {
    public static BaseResponse getBaseResponse(Object data) {
        return new BaseResponse(Constants.STATUS_CODE_SUCCESS, Constants.MSG_SUCCESS, data);
    }


    public static  BaseResponse getBaseResponse(int statusCode, String message) {
        return new BaseResponse(statusCode, message, null);
    }
}
