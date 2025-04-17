package org.notifyservice.interceptor;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
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
public class JwtAuthHandshakeInterceptor implements HandshakeInterceptor {

    final private JwtService jwtService;
    final private UserService userService;

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {

        var authHeader = request.getHeaders().getFirst(HEADER_NAME);

        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer "))
            return false;

        var jwt = authHeader.substring(BEARER_PREFIX.length());

        if(jwtService.isTokenValid(jwt))
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

        return true;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {
    }
}
