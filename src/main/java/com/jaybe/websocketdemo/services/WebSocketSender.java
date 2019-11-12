package com.jaybe.websocketdemo.services;

public interface WebSocketSender<T> {

    void broadCastByUserName(String userName, T payload, Class<T> payloadType);

    void broadCastForAllConnections(T payload);
}
