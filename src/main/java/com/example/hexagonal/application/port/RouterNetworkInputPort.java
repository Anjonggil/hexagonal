package com.example.hexagonal.application.port;

import com.example.hexagonal.application.usecase.RouterNetworkUseCase;
import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.service.NetworkOperation;
import com.example.hexagonal.domain.vo.Network;
import com.example.hexagonal.domain.vo.RouterId;
import lombok.var;

public class RouterNetworkInputPort implements RouterNetworkUseCase {

    private final RouterNetworkOutputPort routerNetworkOutputPort;

    private final NotifyEventOutputPort notifyEventOutputPort;

    public RouterNetworkInputPort(RouterNetworkOutputPort routerNetworkOutputPort, NotifyEventOutputPort notifyOutputPort) {
        this.routerNetworkOutputPort = routerNetworkOutputPort;
        this.notifyEventOutputPort = notifyOutputPort;
    }

    @Override
    public Router addNetworkToRouter(RouterId routerId, Network network) throws IllegalAccessException {
        var router = fetchRouter(routerId);
        return createNetwork(router, network);
    }

    @Override
    public Router getRouter(RouterId routerId) {
        notifyEventOutputPort.sendEvent("Retrieving router ID" + routerId.getId());
        return fetchRouter(routerId);
    }

    private Router createNetwork(Router router, Network network) throws IllegalAccessException {
        var newRouter = NetworkOperation.createNewNetwork(router,network);
        notifyEventOutputPort.sendEvent("Adding " + network.getName() + " network to router " + router.getRouterId().getId());
        return persistNetwork(router) ? newRouter : router;
    }

    private boolean persistNetwork(Router router) {
        return routerNetworkOutputPort.persistRouter(router);
    }

    private Router fetchRouter(RouterId routerId) {
        return routerNetworkOutputPort.fetchRouterById(routerId);
    }
}
