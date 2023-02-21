package com.example.hexagonal;

import com.example.hexagonal.application.port.NotifyEventOutputPort;
import com.example.hexagonal.application.port.RouterNetworkInputPort;
import com.example.hexagonal.application.port.RouterNetworkOutputPort;
import com.example.hexagonal.application.usecase.RouterNetworkUseCase;
import com.example.hexagonal.framework.adapter.in.RouterManageNetworkAdapter;
import com.example.hexagonal.framework.adapter.in.file.RouterNetworkCLIAdapter;
import com.example.hexagonal.framework.adapter.in.rest.RouterNetworkRestAdapter;
import com.example.hexagonal.framework.adapter.in.websocket.NotifyEventWebSocketAdapter;
import com.example.hexagonal.framework.adapter.out.file.RouterNetworkFileAdapter;
import com.example.hexagonal.framework.adapter.out.h2.RouterNetworkH2Adapter;
import com.example.hexagonal.framework.adapter.out.kafka.NotifyEventKafkaAdapter;
import com.sun.net.httpserver.HttpServer;
import lombok.var;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class App {

    RouterManageNetworkAdapter inputAdapter;
    RouterNetworkUseCase useCase;
    RouterNetworkOutputPort routerOutputPort;

    NotifyEventOutputPort notifyOutputPort;

    public static void main(String[] args) throws IllegalAccessException, IOException, InterruptedException {
        var adapter = "cli";
        if (args.length>0){
            adapter = args[0];
        }
        new App().setAdapter(adapter);
    }

    void setAdapter(String adapter) throws IllegalAccessException, IOException, InterruptedException {
        switch (adapter) {
            case "rest" :
                routerOutputPort = RouterNetworkH2Adapter.getInstance();
                notifyOutputPort = NotifyEventKafkaAdapter.getInstance();
                useCase = new RouterNetworkInputPort(routerOutputPort, notifyOutputPort);
                inputAdapter= new RouterNetworkRestAdapter(useCase);
                rest();
                NotifyEventWebSocketAdapter.startServer();
                break;
            default:
                routerOutputPort = RouterNetworkFileAdapter.getInstance();
                useCase = new RouterNetworkInputPort(routerOutputPort, notifyOutputPort);
                inputAdapter = new RouterNetworkCLIAdapter(useCase);
                cli();
        }
    }

    private void cli() throws IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        inputAdapter.processRequest(scanner);
    }

    private void rest(){
        try {
            System.out.println("REST endpoint listening on port 8080...");
            var httpserver = HttpServer.create(new InetSocketAddress(8080),0);
            inputAdapter.processRequest(httpserver);
        } catch (IOException | IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
