package org.notifyservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

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
                .simpDestMatchers("/user/{username}/**")
                    .access("@webSocketSecurityService.canAccessPrincipal(authentication, #username)")
                .simpDestMatchers("/topic/**", "/queue/**").denyAll()
                .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
                .anyMessage().denyAll();

    }
}