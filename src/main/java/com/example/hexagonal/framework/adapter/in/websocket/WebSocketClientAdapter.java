package com.example.hexagonal.framework.adapter.in.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketClientAdapter extends WebSocketClient {

    public WebSocketClientAdapter(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("opened connection");
    }

    @Override
    public void onMessage(String message) {
        String channel = message;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed connection");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
