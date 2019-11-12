package com.jaybe.websocketdemo.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaybe.websocketdemo.repositories.WebSocketSessionsStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
public class WebSocketSenderService implements WebSocketSender {

    private final WebSocketSessionsStore webSocketSessionsStore;
    private ObjectMapper mapper;

    public WebSocketSenderService(WebSocketSessionsStore webSocketSessionsStore) {
        this.webSocketSessionsStore = webSocketSessionsStore;
        mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
    }

    //@Scheduled(initialDelay = 20000, fixedDelay = 20000L)
    public void sendMessage() {
        webSocketSessionsStore.getWebSocketSessions("foo")
                .ifPresent(webSocketSessions -> {
                    webSocketSessions.forEach(webSocketSession -> {
                        try {
                            webSocketSession.sendMessage(new TextMessage("Hello from scheduled func."));
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                });
    }

    @Override
    public void broadCastByUserName(String userName, Object payload) {
        Objects.requireNonNull(userName);
        Objects.requireNonNull(payload);

        webSocketSessionsStore.getWebSocketSessions(userName)
                .ifPresent(webSocketSessions -> {
                    webSocketSessions.forEach(webSocketSession -> {
                        try {
                            webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsString(payload)));
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                });
    }

    @Override
    public void broadCastForAllConnections(Object payload) {

    }
}
