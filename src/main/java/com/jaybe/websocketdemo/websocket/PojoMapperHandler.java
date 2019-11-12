package com.jaybe.websocketdemo.websocket;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaybe.websocketdemo.models.TestMessage;
import com.jaybe.websocketdemo.repositories.WebSocketSessionsStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class PojoMapperHandler extends TextWebSocketHandler {

    private final WebSocketSessionsStore webSocketSessionsStore;
    private ObjectMapper mapper;

    public PojoMapperHandler(WebSocketSessionsStore webSocketSessionsStore) {
        this.webSocketSessionsStore = webSocketSessionsStore;
        mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessionsStore.addWebSocketSession(Objects.requireNonNull(session.getPrincipal()).getName(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Object payload = message.getPayload();
        var convertedMessage = mapper.readValue(payload.toString(), TestMessage.class);
        log.info("Retrieved message - {}", convertedMessage);
        this.webSocketSessionsStore.getWebSocketSessions(Objects.requireNonNull(session.getPrincipal()).getName())
                .ifPresent(webSocketSessions -> {
                    webSocketSessions.forEach(webSocketSession -> {
                        try {
                            webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsString(convertedMessage)));
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                });

    }
}
