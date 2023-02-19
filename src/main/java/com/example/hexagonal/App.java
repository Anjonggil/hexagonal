package com.example.hexagonal;

import com.example.hexagonal.application.port.RouterNetworkInputPort;
import com.example.hexagonal.application.port.RouterNetworkOutputPort;
import com.example.hexagonal.application.usecase.RouterNetworkUseCase;
import com.example.hexagonal.framework.adapter.in.RouterNetworkAdapter;
import com.example.hexagonal.framework.adapter.in.RouterNetworkRestAdapter;
import com.example.hexagonal.framework.adapter.out.h2.RouterNetworkH2Adapter;
import com.sun.net.httpserver.HttpServer;
import lombok.var;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class App {

    RouterNetworkAdapter inputAdapter;
    RouterNetworkUseCase useCase;
    RouterNetworkOutputPort outputPort;

    public static void main(String[] args) {
        var adapter = "cli";
        if (args.length>0){
            adapter = args[0];
        }
        new App().setAdapter(adapter);
    }

    void setAdapter(String adapter) {
        switch (adapter) {
            case "rest" :
                outputPort = RouterNetworkH2Adapter.getInstance();
                useCase = new RouterNetworkInputPort(outputPort);
                inputAdapter = new RouterNetworkRestAdapter(useCase);
                rest();
                break;
            default:
                outputPort = RouterNetworkFileAdapter.getInstance();
                useCase = new RouterNetworkInputPort(outputPort);
                inputAdapter = new RouterNetworkCLIAdapter(useCase);
                cli();
        }
    }

    private void cli(){
        Scanner scanner = new Scanner(System.in);
        inputAdapter.processRequest(scanner);
    }

    private void rest(){
        try {
            System.out.println("REST endpoint listening on port 8080...");
            var httpserver = HttpServer.create(new InetSocketAddress(8080),0);
            inputAdapter.processRequest(httpserver);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
