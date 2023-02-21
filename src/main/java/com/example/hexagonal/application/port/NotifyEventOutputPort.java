package com.example.hexagonal.application.port;

public interface NotifyEventOutputPort {
    void sendEvent(String event);
    String getEvent();
}
