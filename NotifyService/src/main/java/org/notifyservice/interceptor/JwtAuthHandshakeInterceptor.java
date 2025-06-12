package org.notifyservice.interceptor;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.common.service.JwtService;
import org.common.service.UserService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

import static org.common.filter.JwtAuthFilter.BEARER_PREFIX;
import static org.common.filter.JwtAuthFilter.HEADER_NAME;

@Component
@RequiredArgsConstructor
@Slf4j // ← добавляем аннотацию для логирования
public class JwtAuthHandshakeInterceptor implements HandshakeInterceptor {

    final private JwtService jwtService;
    final private UserService userService;

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {

        String query = request.getURI().getQuery(); // example: token=ey...
        String jwt = null;
        if (query != null && query.startsWith("token=")) {
            jwt = query.substring("token=".length());
            log.info("JWT из query параметра: {}", jwt);
        }


        if (StringUtils.isEmpty(jwt) || !jwtService.isTokenValid(jwt))
            return false;

        var username = jwtService.extractUserName(jwt);
        var userDetails = userService.userDetails(username);

        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        attributes.put("authToken", authToken);
        attributes.put("jwt", jwt);
        log.info("AuthSession created for user: {}", username);
        return true;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {
    }
}
