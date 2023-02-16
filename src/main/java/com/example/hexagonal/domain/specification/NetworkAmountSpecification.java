package com.example.hexagonal.domain.specification;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.specification.share.AbstractSpecification;

public class NetworkAmountSpecification extends AbstractSpecification<Router> {

    final static public int MAXIMUM_ALLOWED_NETWORKS = 6;
    @Override
    public boolean isSatisfiedBy(Router router) {
        return router.retrieveNetworks().size() <= MAXIMUM_ALLOWED_NETWORKS;
    }
}
