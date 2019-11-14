package com.jaybe.websocketdemo.controllers;

import com.jaybe.websocketdemo.controllers.response.GenericResponse;
import com.jaybe.websocketdemo.repositories.WebSocketSessionsStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class DemoController extends AbstractRestController {

    private final WebSocketSessionsStore webSocketSessionsStore;

    public DemoController(WebSocketSessionsStore webSocketSessionsStore) {
        this.webSocketSessionsStore = webSocketSessionsStore;
    }

    /**
     * For getting JSESSIONID cookie
     * @return hello if basic auth success
     */
    @GetMapping(path = "/hello")
    public String greetings() {
        return "Hello!";
    }

    @GetMapping(path = "/time")
    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }

    @GetMapping(path = "/sessions")
    public GenericResponse getSessions() {
        var cache = webSocketSessionsStore.getCache();

        var response = new GenericResponse<Map<String, List<String>>>();

        response.setData(cache.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().map(WebSocketSession::getId).collect(Collectors.toList()))));
        return response;
    }
}
