package com.ducnd.security.withoutloginregister;

import com.ducnd.Constants;
import com.ducnd.security.UserContext;
import com.ducnd.security.exception.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;


/**
 * Created by ducnd on 6/25/17.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private UserContext userContext;

    public JwtAuthenticationToken(String token) {
        super(null);
        Jws<Claims> claimsJws = parseClaims(token, Constants.KEY_TOKEN);
        String username = (String) claimsJws.getBody().get("name");
        this.userContext = new UserContext(username, token);
        this.setAuthenticated(false);
    }

    @Override
    public UserContext getCredentials() {
        return userContext;
    }

    private static Jws<Claims> parseClaims(String token, String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            throw new JwtExpiredTokenException(expiredEx.getMessage(), token, expiredEx);
        }
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
