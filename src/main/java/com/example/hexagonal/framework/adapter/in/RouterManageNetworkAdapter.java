package com.example.hexagonal.framework.adapter.in;

import com.example.hexagonal.application.usecase.RouterNetworkUseCase;
import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.vo.IP;
import com.example.hexagonal.domain.vo.Network;
import com.example.hexagonal.domain.vo.RouterId;
import lombok.var;

import java.util.Map;

public abstract class RouterManageNetworkAdapter {
    protected Router router;
    protected RouterNetworkUseCase routerNetworkUseCase;

    protected Router addNetworkToRouter(Map<String, String> params) throws IllegalAccessException {
        var routerId = RouterId.withId(params.get("routerId"));
        var network = new Network(IP.fromAddress(params.get("address")),
                params.get("name"),
                Integer.valueOf(params.get("cidr")));
        return routerNetworkUseCase.addNetworkToRouter(routerId,network);
    }

    public abstract Router processRequest(Object requestParams) throws IllegalAccessException;

    public Router getRouter(Map<String, String> params){
        var routerId = RouterId.withId(params.get("routerId"));
        return routerNetworkUseCase.getRouter(routerId);
    }
}
