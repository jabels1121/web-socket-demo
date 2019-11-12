package com.jaybe.websocketdemo.services;

public interface WebSocketSender<T> {

    void broadCastByUserName(String userName, T payload);

    void broadCastForAllConnections(T payload);
}
