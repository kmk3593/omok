package com.osite.omok.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.osite.omok.handler.WebSocketHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketOmokConfig implements WebSocketConfigurer {
	
	private final WebSocketHandler webSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(webSocketHandler, "/ws/omok").setAllowedOrigins("*");
	}

}
