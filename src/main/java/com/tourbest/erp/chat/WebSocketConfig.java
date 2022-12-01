package com.tourbest.erp.chat;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@AllArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private ChatBridge chatBridge;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatBridge, "chat").setAllowedOrigins("*");
    }
}