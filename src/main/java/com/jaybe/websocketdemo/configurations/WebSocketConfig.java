package com.jaybe.websocketdemo.configurations;

import com.jaybe.websocketdemo.websocket.MyCustomHandler;
import com.jaybe.websocketdemo.websocket.MyHttpSessionHandshakeInterceptor;
import com.jaybe.websocketdemo.websocket.PojoMapperHandler;
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
    private final PojoMapperHandler pojoMapperHandler;

    public WebSocketConfig(MyHttpSessionHandshakeInterceptor myHttpSessionHandshakeInterceptor,
                           MyCustomHandler myCustomHandler,
                           PojoMapperHandler pojoMapperHandler) {
        this.myHttpSessionHandshakeInterceptor = myHttpSessionHandshakeInterceptor;
        this.myCustomHandler = myCustomHandler;
        this.pojoMapperHandler = pojoMapperHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myCustomHandler, "/ws-demo")
                .addInterceptors(myHttpSessionHandshakeInterceptor).setAllowedOrigins("*");

        webSocketHandlerRegistry.addHandler(pojoMapperHandler, "/ws-pojo").setAllowedOrigins("*");
    }

}
