package com.ducnd.security.regiseter;

import com.ducnd.manager.BaseManager;
import com.ducnd.tables.UserProfile;
import com.ducnd.tables.records.UserProfileRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by ducnd on 6/25/17.
 */
@Component
public class AjaxRegisterProvider implements AuthenticationProvider {
    @Autowired
    private BaseManager baseManager;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AjaxAuthenRegister register = (AjaxAuthenRegister) authentication;
        UserProfileRecord userRecord = baseManager.getDslContext()
                .selectFrom(UserProfile.USER_PROFILE)
                .where(UserProfile.USER_PROFILE.USERNAME
                        .eq(register.getPrincipal()
                                .getUsername())).fetchOne();
        if (userRecord != null) {
            throw new AuthenticationServiceException("user exsit");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = passwordEncoder.encode(register.getCredentials());
        int indexCollum = baseManager.getDslContext().insertInto(UserProfile.USER_PROFILE, UserProfile.USER_PROFILE.USERNAME, UserProfile.USER_PROFILE.PASSWORD, UserProfile.USER_PROFILE.TOKEN)
                .values(register.getPrincipal().getUsername(), passwordEncode, register.getPrincipal().getToken()).execute();
        if (indexCollum <= 0) {
            throw new AuthenticationServiceException("Can not insert user into database");
        }
        return new UsernamePasswordAuthenticationToken(register.getPrincipal(), register.getCredentials());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(AjaxAuthenRegister.class);
    }
}
