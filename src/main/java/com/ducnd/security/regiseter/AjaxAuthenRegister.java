package com.ducnd.security.regiseter;

import com.ducnd.Constants;
import com.ducnd.security.UserContext;
import com.ducnd.security.Utils;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Created by ducnd on 6/25/17.
 */
public class AjaxAuthenRegister extends AbstractAuthenticationToken {
    private UserContext userContext;
    private String password;

    public AjaxAuthenRegister(String username, String password) {
        super(null);
        this.password = password;
        String token = Utils.getToken(username, Constants.KEY_TOKEN);
        userContext = new UserContext(username, token);
    }

    @Override
    public UserContext getPrincipal() {
        return userContext;
    }

    @Override
    public String getCredentials() {
        return password;
    }
}
