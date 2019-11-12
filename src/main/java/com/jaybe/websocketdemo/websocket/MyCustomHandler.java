package com.jaybe.websocketdemo.websocket;

import com.jaybe.websocketdemo.repositories.WebSocketSessionsStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class MyCustomHandler extends TextWebSocketHandler {

    private final WebSocketSessionsStore wsSessionStore;

    MyCustomHandler(WebSocketSessionsStore wsSessionStore) {
        this.wsSessionStore = wsSessionStore;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        wsSessionStore.addWebSocketSession(Objects.requireNonNull(session.getPrincipal()).getName(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        var username = Objects.requireNonNull(session.getPrincipal()).getName();

        wsSessionStore.removeWebSocketSession(username, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        this.wsSessionStore.getWebSocketSessions(Objects.requireNonNull(session.getPrincipal()).getName())
                .ifPresent(webSocketSessions -> {
                    webSocketSessions.forEach(webSocketSession -> {
                        try {
                            webSocketSession.sendMessage(new TextMessage("Hello from server"));
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                });
    }


}
