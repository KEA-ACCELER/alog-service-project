package kea.alog.project.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kea.alog.project.common.exception.UnauthorizationException;
import kea.alog.project.common.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;


@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new UnauthorizationException(403, "AUTHORIZATION");
        }
        request.setAttribute("user", jwtProvider.getPayload(token.replace("Bearer ", "")));
        return true;
    }
}
