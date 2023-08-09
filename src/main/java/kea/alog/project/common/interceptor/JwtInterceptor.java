package kea.alog.project.common.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kea.alog.project.common.dto.TokenPayloadDto;
import kea.alog.project.common.exception.UnauthorizationException;
import kea.alog.project.common.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;


@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws JsonProcessingException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new UnauthorizationException(403, "AUTHORIZATION");
        }
        String payload = jwtProvider.getPayload(token.replace("Bearer ", ""));
        TokenPayloadDto user = jwtProvider.getUserInfo(payload);
        request.setAttribute("user", user);
        return true;
    }
}
