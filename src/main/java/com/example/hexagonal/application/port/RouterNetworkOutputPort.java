package com.example.hexagonal.application.port;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.vo.RouterId;

public interface RouterNetworkOutputPort {
    boolean persistRouter(Router router);

    Router fetchRouterById(RouterId routerId);
}
