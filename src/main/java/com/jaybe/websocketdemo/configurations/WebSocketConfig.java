package com.jaybe.websocketdemo.configurations;

import com.jaybe.websocketdemo.aop.sockets.MyCustomHandler;
import com.jaybe.websocketdemo.aop.sockets.MyHttpSessionHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    private final MyHttpSessionHandshakeInterceptor myHttpSessionHandshakeInterceptor;
    private final MyCustomHandler myCustomHandler;

    public WebSocketConfig(MyHttpSessionHandshakeInterceptor myHttpSessionHandshakeInterceptor, MyCustomHandler myCustomHandler) {
        this.myHttpSessionHandshakeInterceptor = myHttpSessionHandshakeInterceptor;
        this.myCustomHandler = myCustomHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myCustomHandler, "/ws-demo")
                .addInterceptors(myHttpSessionHandshakeInterceptor).setAllowedOrigins("*");
    }

}
