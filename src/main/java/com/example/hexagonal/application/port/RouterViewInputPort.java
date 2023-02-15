package com.example.hexagonal.application.port;

import com.example.hexagonal.application.usecase.RouterViewUseCase;
import com.example.hexagonal.domain.Router;
import lombok.var;

import java.util.List;
import java.util.function.Predicate;

public class RouterViewInputPort implements RouterViewUseCase {

    private RouterViewOutputPort routerViewOutputPort;

    public RouterViewInputPort(RouterViewOutputPort routerViewOutputPort) {
        this.routerViewOutputPort = routerViewOutputPort;
    }

    @Override
    public List<Router> getRouters(Predicate<Router> filter) {
        var routers = routerViewOutputPort.fetchRouters();
        return Router.retrieveRouter(routers,filter);
    }
}
