package com.jaybe.websocketdemo.services;

import com.jaybe.websocketdemo.repositories.WebSocketSessionsStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Service
@Slf4j
public class WebSocketSenderService {

    private final WebSocketSessionsStore webSocketSessionsStore;

    public WebSocketSenderService(WebSocketSessionsStore webSocketSessionsStore) {
        this.webSocketSessionsStore = webSocketSessionsStore;
    }

    @Scheduled(initialDelay = 5000L, fixedDelay = 5000L)
    public void sentMessage() {
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
}
