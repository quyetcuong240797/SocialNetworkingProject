package com.ducnd;

/**
 * Created by Lap trinh on 3/28/2018.
 */
public interface Constants {
    String KEY_TOKEN = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    String ENPOINT_LOGIN = "/auth/login";
//    String ENPOINT_UPDATEUSER = "/auth/update{username}/{city}";//@RequestParam
String ENPOINT_UPDATEUSER = "/auth/update/{username}";//@PathVariable
    String ENPOINT_REGISTER = "/auth/register";
    String ENPOINT_FINDUSER = "/auth/finduser";
    String ENPOINT_MATCH_API = "/api/**";
    long TIME_TOKEN_EXPIRE = 3600000000L;
    int STATUS_CODE_SUCCESS = 0;
    String MSG_SUCCESS = "success";
    int STATUS_CODE_AUTHEN = 401;
    String MSG_AUTHEN = "Token invalidate";
    String TOKEN_NAME = "token";
    int MIN_LEHGTH_PASSWORD = 6;

    int STATUS_CODE_TOKEN_IN_VALID = 10;
    int STATUS_CODE_EXPIRED_TOKEN = 20;
    int STATUS_CODE_USERNAME_OR_PASSWORD_INVALID = 30;

    int STATUS_ERROR_PARAM = 100;
}
