package com.example.hexagonal.application.usecase;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.vo.Network;
import com.example.hexagonal.domain.vo.RouterId;

public interface RouterNetworkUseCase {
    Router addNetworkToRouter(RouterId routerId, Network network) throws IllegalAccessException;

    Router getRouter(RouterId routerId);
}
