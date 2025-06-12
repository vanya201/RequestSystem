/*
package org.notifyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
@EnableWebSocketSecurity
public class WebSocketAuthorizationConfig  extends AbstractSecurityWebSocketMessageBrokerConfigurer  {

    @Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(
            MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
                .simpTypeMatchers(SimpMessageType.CONNECT,
                        SimpMessageType.HEARTBEAT,
                        SimpMessageType.UNSUBSCRIBE,
                        SimpMessageType.DISCONNECT)
                .permitAll()
                .simpSubscribeDestMatchers("/user/**", "/topic/**", "/queue/**").hasRole("USER")
                .simpDestMatchers("/app/**").hasRole("USER")
                .anyMessage().denyAll();
        return messages.build();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}*/
