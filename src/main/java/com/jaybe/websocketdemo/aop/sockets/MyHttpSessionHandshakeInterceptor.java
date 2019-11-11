package com.jaybe.websocketdemo.aop.sockets;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MyHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        var principal = (UsernamePasswordAuthenticationToken) request.getPrincipal();
        if (principal == null) return false;

        AtomicBoolean res = new AtomicBoolean(false);

        principal.getAuthorities().forEach(grantedAuthority -> {
            if (grantedAuthority.getAuthority().equalsIgnoreCase("ADMIN")) {
                res.set(true);
            }
        });

        return super.beforeHandshake(request, response, wsHandler, attributes)
                &&
                principal.isAuthenticated()
                &&
                res.get();
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
