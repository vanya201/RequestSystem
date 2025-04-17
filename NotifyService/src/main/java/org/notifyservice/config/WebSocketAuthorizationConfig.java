package org.notifyservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;

@Configuration
@EnableWebSocketSecurity
public class WebSocketAuthorizationConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/user/**", "/topic/**", "/queue/**").hasRole("USER")
                .simpDestMatchers("/app/**").hasRole("USER")
                .simpDestMatchers("/user/**").denyAll()
                .simpDestMatchers("/topic/**", "/queue/**").denyAll()
                .anyMessage().denyAll();
    }
}