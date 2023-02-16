package com.example.hexagonal.domain.specification;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.specification.share.AbstractSpecification;
import com.example.hexagonal.domain.vo.RouterType;

public class RouterTypeSpecification extends AbstractSpecification<Router> {
    @Override
    public boolean isSatisfiedBy(Router router) {
        return router.getRouterType().equals(RouterType.EDGE) || router.getRouterType().equals(RouterType.CORE);
    }
}
