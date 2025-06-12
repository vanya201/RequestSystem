package org.notifyservice.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {

        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) accessor.getSessionAttributes().get("authToken");
            accessor.setUser(user);
        }

        return message;
    }
}
