package com.example.hexagonal.domain.service;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.specification.CIDRSpecification;
import com.example.hexagonal.domain.specification.NetworkAmountSpecification;
import com.example.hexagonal.domain.specification.NetworkAvailabilitySpecification;
import com.example.hexagonal.domain.specification.RouterTypeSpecification;
import com.example.hexagonal.domain.vo.IP;
import com.example.hexagonal.domain.vo.Network;
import lombok.var;

public class NetworkOperation {

    private final int MINIMUM_ALLOWED_CIDR = 8;

    public static Router createNewNetwork(Router router, Network network) throws IllegalAccessException {
        var availabilitySpec = new NetworkAvailabilitySpecification(network.getAddress(), network.getName(), network.getCidr());
        var cidrSpec = new CIDRSpecification();
        var routerTypeSpec = new RouterTypeSpecification();
        var amountSpec = new NetworkAmountSpecification();

        if (cidrSpec.isSatisfiedBy(network.getCidr()))  throw new IllegalArgumentException("CIDR is below "+CIDRSpecification.MINIMUM_ALLOWED_CIDR);

        if (!availabilitySpec.isSatisfiedBy(router)) throw new IllegalArgumentException("Address already exist");

        if (amountSpec.and(routerTypeSpec).isSatisfiedBy(router)){
            Network newNetwork = router.createNetwork(network.getAddress(), network.getName(), network.getCidr());
            router.addNetworkToSwitch(newNetwork);
        }

        return router;

    }
}
