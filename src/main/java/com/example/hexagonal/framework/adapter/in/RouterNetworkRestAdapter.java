package com.example.hexagonal.framework.adapter.in;

import com.example.hexagonal.application.usecase.RouterNetworkUseCase;
import com.example.hexagonal.domain.entity.Router;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import lombok.var;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.*;

import static java.util.stream.Collectors.*;

public class RouterNetworkRestAdapter extends RouterNetworkAdapter{

    public RouterNetworkRestAdapter(RouterNetworkUseCase routerNetworkUseCase) {
        this.routerNetworkUseCase = routerNetworkUseCase;
    }

    @Override
    public Router processRequest(Object requestParams) {
        Map<String, String> params = new HashMap<>();
        if (requestParams instanceof HttpServer){
            var httpServer = (HttpServer) requestParams;
            httpServer.createContext("/network/add", (exchange -> {
                if ("GET".equals(exchange.getRequestMethod())){
                    var query = exchange.getRequestURI().getRawQuery();
                    httpParams(query, params);
                    try {
                        router = this.addNetworkToRouter(params);
                        ObjectMapper mapper = new ObjectMapper();
                        var routerJson = mapper.writeValueAsString(RouterJsonFileMapper.toJson(router));
                        exchange.getResponseHeaders().set("Content-Type","application/json");
                        exchange.sendResponseHeaders(200, routerJson.getBytes().length);
                        OutputStream output = exchange.getResponseBody();
                        output.write(routerJson.getBytes());
                        output.flush();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    ;
                }
            }));
        }
        return null;
    }

    private void httpParams(String query, Map<String, String> params) {
        var noNameText = "Anonymous";
        var requestParams = Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="),2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));
        var routerId = requestParams.getOrDefault("routerId", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("routerId",routerId);
        var address = requestParams.getOrDefault("address", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("address",address);
        var name = requestParams.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("name",name);
        var cidr = requestParams.getOrDefault("cidr", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("cidr",cidr);
    }

    private static String decode(final String encoded){
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        }  catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is a required encoding", e);
        }
    }
}
