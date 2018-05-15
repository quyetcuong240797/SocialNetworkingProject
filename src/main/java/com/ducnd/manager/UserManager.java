package com.ducnd.manager;

import com.ducnd.Constants;
import com.ducnd.model.request.LoginRequest;
import com.ducnd.model.request.RegisterRequest;
import com.ducnd.model.response.ResponseUtils;
import com.ducnd.model.response.response.RegisterResponse;
import com.ducnd.security.Utils;
import com.ducnd.tables.UserProfile;
import com.ducnd.tables.records.UserProfileRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//import kieu vip
import static com.ducnd.Tables.*;

@Component
public class UserManager extends BaseManager {
    @Autowired
    private Environment environment;
    private BCryptPasswordEncoder endCode = new BCryptPasswordEncoder();

    public Object register(RegisterRequest request) {
        if (request.getUsername() == null) {
            return ResponseUtils.getBaseResponse(Constants.STATUS_ERROR_PARAM, "Error param");
        }
        if (request.getEmail() == null) {
            return ResponseUtils.getBaseResponse(Constants.STATUS_ERROR_PARAM, "Error param");
        }
        if (request.getPassword() == null) {
            return ResponseUtils.getBaseResponse(Constants.STATUS_ERROR_PARAM, "Error param");
        }
        boolean isExist = dslContext.fetchExists(USER_PROFILE,
                USER_PROFILE.USERNAME.eq(request.getUsername())
                        .or(USER_PROFILE.EMAIL.eq(request.getEmail()))
        );
        if (isExist) {
            return ResponseUtils.getBaseResponse(Constants.STATUS_ERROR_PARAM, "User existed");
        }
        String codePassword = endCode.encode(request.getPassword());
        String token = Utils.getToken(request.getUsername(), environment.getProperty("demo.security.jwt.tokenSigningKey"));
        UserProfileRecord record = dslContext.insertInto(USER_PROFILE,
                USER_PROFILE.USERNAME, USER_PROFILE.EMAIL, USER_PROFILE.PASSWORD, USER_PROFILE.TOKEN)
                .values(request.getUsername(), request.getEmail(), codePassword, token)
                .returning().fetchOne();

        RegisterResponse response = new RegisterResponse();
        response.setToken(record.getToken());

        return ResponseUtils.getBaseResponse(response);
    }
    public Object login(LoginRequest request){
        boolean isExist = dslContext.fetchExists(USER_PROFILE,
                USER_PROFILE.USERNAME.eq(request.getUsername()));
        String token;
            if(isExist){
               String codePassword=dslContext.select(UserProfile.USER_PROFILE.PASSWORD).from(UserProfile.USER_PROFILE)
                       .where(UserProfile.USER_PROFILE.USERNAME.eq(endCode.encode(request.getPassword()))).fetchAny().value1();

                token=dslContext.select(UserProfile.USER_PROFILE.TOKEN).from(UserProfile.USER_PROFILE).
                       where(UserProfile.USER_PROFILE.USERNAME.eq(endCode.encode(request.getPassword()))).fetchAny().value1();
            }
            else {
                return ResponseUtils.getBaseResponse(Constants.STATUS_CODE_USERNAME_OR_PASSWORD_INVALID,"Error param");

            }
        return ResponseUtils.getBaseResponse(token);
    }

}
