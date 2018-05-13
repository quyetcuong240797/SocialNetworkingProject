package com.ducnd.security.login;

import com.ducnd.manager.BaseManager;
import com.ducnd.tables.UserProfile;
import com.ducnd.tables.records.UserProfileRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by ducnd on 6/25/17.
 */
@Component
public class AjaxAuthenLoginProvider implements AuthenticationProvider {
    @Autowired
    private BaseManager baseManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AjaxAuthenLogin authenLogin = (AjaxAuthenLogin) authentication;

        UserProfileRecord userRecord = baseManager.getDslContext().selectFrom(UserProfile.USER_PROFILE).where(UserProfile.USER_PROFILE.USERNAME.eq(authenLogin.getPrincipal().getUsername())).fetchOne();
        if (userRecord == null) {
            throw new UsernameNotFoundException("user not exist");
        }
        if (!new BCryptPasswordEncoder().matches(authenLogin.getCredentials(), userRecord.getPassword())) {
            throw new AuthenticationServiceException("password incorrect");
        }
        baseManager.getDslContext().update(UserProfile.USER_PROFILE).set(UserProfile.USER_PROFILE.TOKEN, authenLogin.getPrincipal().getToken()).where(UserProfile.USER_PROFILE.ID.eq(userRecord.getId())).execute();
        return new UsernamePasswordAuthenticationToken(
                authenLogin.getPrincipal(),
                authenLogin.getCredentials(),
                new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(
                AjaxAuthenLogin.class);
    }
}
