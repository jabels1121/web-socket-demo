package com.jaybe.websocketdemo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class MyHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final String WS_USER_ROLE = "WS_USER";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        var principal = (UsernamePasswordAuthenticationToken) request.getPrincipal();
        if (principal == null) return false;

        /*if (!checkPrincipalAuthorities(principal)) {
            throw new AccessDeniedException("Not authorize");
        }*/

        return super.beforeHandshake(request, response, wsHandler, attributes) && principal.isAuthenticated();
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

    private boolean checkPrincipalAuthorities(UsernamePasswordAuthenticationToken principal) {
        AtomicBoolean result = new AtomicBoolean(false);
        principal.getAuthorities()
                .forEach(grantedAuthority -> {
                    if (grantedAuthority.getAuthority().equalsIgnoreCase(WS_USER_ROLE)) {
                        result.set(true);
                    }
                });
        return result.get();
    }
}
