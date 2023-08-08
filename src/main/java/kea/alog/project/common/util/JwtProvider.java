package kea.alog.project.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    public String getPayload(String token) {
        DecodedJWT jwt = JWT.decode(token);
        System.out.println(jwt);
        return jwt.getPayload();
    }
}
