package com.jaybe.websocketdemo.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@Slf4j
public class WebSocketSessionsStore {

    private final ConcurrentHashMap<String, List<WebSocketSession>> webSocketSessionCache;

    public WebSocketSessionsStore() {
        this.webSocketSessionCache = new ConcurrentHashMap<>();
    }

    public Optional<List<WebSocketSession>> getWebSocketSessions(String key) {
        log.info("Get wsSession from store by key - {}", key);
        return Optional.ofNullable(this.webSocketSessionCache.get(key));
    }

    public void addWebSocketSession(String key, WebSocketSession webSocketSession) {
        log.info("Add wsSession to store - {}", webSocketSession);
        if (null != this.webSocketSessionCache.get(key)) {
            this.webSocketSessionCache.get(key).add(webSocketSession);
        } else {
            this.webSocketSessionCache.put(key, new CopyOnWriteArrayList<>(Collections.singletonList(webSocketSession)));
        }
    }

    public void removeWebSocketSession(String key, WebSocketSession socketSession) {
        log.info("Remove wsSession from store by key - {}", key);
        this.webSocketSessionCache.get(key).remove(socketSession);
    }
}
