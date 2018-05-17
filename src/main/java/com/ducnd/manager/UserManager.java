package com.ducnd.manager;

import com.ducnd.Constants;
import com.ducnd.model.request.LoginRequest;
import com.ducnd.model.request.RegisterRequest;
import com.ducnd.model.response.MessageResponse;
import com.ducnd.model.response.ResponseUtils;
import com.ducnd.model.response.response.LoginResponse;
import com.ducnd.model.response.response.RegisterResponse;
import com.ducnd.security.Utils;
import com.ducnd.tables.UserProfile;
import com.ducnd.tables.records.UserProfileRecord;
import org.jooq.DSLContext;
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
    public Object findUser(String username){
        UserProfileRecord record=dslContext.selectFrom(UserProfile.USER_PROFILE).
                where(UserProfile.USER_PROFILE.USERNAME.eq(username)).fetchAny();

        com.ducnd.tables.pojos.UserProfile userProfile=new com.ducnd.tables.pojos.UserProfile();
        userProfile.setId(record.getId());
        userProfile.setPassword(record.getPassword());
        userProfile.setUsername(record.getUsername());
        userProfile.setEmail(record.getEmail());
        userProfile.setAvatar(record.getAvatar());
        userProfile.setBirthday(record.getBirthday());
        userProfile.setCity(record.getCity());
        userProfile.setJob(record.getJob());
        userProfile.setToken(record.getToken());
             return ResponseUtils.getBaseResponse(userProfile);
     //   return userProfile;
        }


public Object updateUser( String username, com.ducnd.tables.pojos.UserProfile userProfileUpdate){
    dslContext.update(UserProfile.USER_PROFILE).
            set(UserProfile.USER_PROFILE.AVATAR,userProfileUpdate.getAvatar()).
                    set(UserProfile.USER_PROFILE.BIRTHDAY,userProfileUpdate.getBirthday()).
                    set(UserProfile.USER_PROFILE.CITY,userProfileUpdate.getCity()).
                    set(UserProfile.USER_PROFILE.JOB,userProfileUpdate.getJob()).
                    set(UserProfile.USER_PROFILE.SEX,userProfileUpdate.getSex())
                    .set(UserProfile.USER_PROFILE.TOKEN,Utils.getToken(userProfileUpdate.getToken(),
                            environment.getProperty("demo.security.jwt.tokenSigningKey")))
                    .where(UserProfile.USER_PROFILE.USERNAME.eq(username)).execute();
    return ResponseUtils.getBaseResponse(Constants.STATUS_CODE_SUCCESS,"Update sucessfully");
}
//public Object findUser(String username){

//}

    }

//    public Object login(LoginRequest request){
//        LoginResponse response=new LoginResponse();
//        int id;
//        String decode_request_password;
//        String decode_respone_password;
//        boolean isExist = dslContext.fetchExists(USER_PROFILE,
//                USER_PROFILE.USERNAME.eq(request.getUsername()));
//        String token;
//            if(isExist){
////
//                token=dslContext.select(UserProfile.USER_PROFILE.TOKEN).from(UserProfile.USER_PROFILE).
//                       where(UserProfile.USER_PROFILE.USERNAME.eq(endCode.encode(request.getPassword()))).fetchAny().value1();
////
//                id=dslContext.select(UserProfile.USER_PROFILE.ID).from(UserProfile.USER_PROFILE).
//                        where(UserProfile.USER_PROFILE.USERNAME.eq(request.getUsername())).fetchAny().value1();
//
//                decode_request_password=endCode.encode(request.getPassword());
//
//                decode_respone_password=dslContext.select(UserProfile.USER_PROFILE.PASSWORD).from(UserProfile.USER_PROFILE)
//                        .where(USER_PROFILE.ID.eq(id)).fetchAny().value1();
//
//                        }
//            else {
//                return ResponseUtils.getBaseResponse(Constants.STATUS_CODE_USERNAME_OR_PASSWORD_INVALID,"Error param");
//
//            }
//        if(decode_request_password.equals(decode_respone_password)){
//            response.setId(id);
//          response.setToken(token);
////        }
////        else
////            return ResponseUtils.getBaseResponse(Constants.STATUS_CODE_USERNAME_OR_PASSWORD_INVALID,"Password not math");
////
////        return Resp  onseUtils.getBaseResponse(response);
//
//    }


