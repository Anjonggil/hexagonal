package com.example.hexagonal.framework.adapter.in.file;

import com.example.hexagonal.application.usecase.RouterNetworkUseCase;
import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.framework.adapter.in.RouterManageNetworkAdapter;
import com.example.hexagonal.framework.adapter.out.file.mapper.RouterJsonFileMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RouterNetworkCLIAdapter extends RouterManageNetworkAdapter {
    public RouterNetworkCLIAdapter(RouterNetworkUseCase routerNetworkUseCase){
        this.routerNetworkUseCase = routerNetworkUseCase;
    }

    @Override
    public Router processRequest(Object requestParams) throws IllegalAccessException {
        Map<String, String> params = stdinParams(requestParams);
        router = this.addNetworkToRouter(params);
        ObjectMapper mapper = new ObjectMapper();
        try {
            var routerJson = mapper.writeValueAsString(RouterJsonFileMapper.toJson(router));
            System.out.println(routerJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return router;
    }

    private Map<String, String> stdinParams(Object requestParams){
        Map<String, String> params = new HashMap<>();
        if(requestParams instanceof Scanner){
            var scanner = (Scanner) requestParams;
            System.out.println("Please inform the Router ID:");
            var routerId = scanner.nextLine();
            params.put("routerId", routerId);
            System.out.println("Please inform the IP address:");
            var address = scanner.nextLine();
            params.put("address", address);
            System.out.println("Please inform the Network Name:");
            var name = scanner.nextLine();
            params.put("name", name);
            System.out.println("Please inform the CIDR:");
            var cidr = scanner.nextLine();
            params.put("cidr", cidr);
        }
        return params;
    }
}
