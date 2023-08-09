package kea.alog.project.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import kea.alog.project.common.dto.TokenPayloadDto;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    public String getPayload(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getPayload();
    }

    public TokenPayloadDto getUserInfo(String payload) throws JsonProcessingException {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String str = new String(decoder.decode(payload));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(str, TokenPayloadDto.class);
    }
}
