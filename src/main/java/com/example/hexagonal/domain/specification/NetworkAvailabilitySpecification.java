package com.example.hexagonal.domain.specification;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.specification.share.AbstractSpecification;
import com.example.hexagonal.domain.vo.IP;
import com.example.hexagonal.domain.vo.Network;
import lombok.var;

public class NetworkAvailabilitySpecification extends AbstractSpecification<Router> {

    private IP address;
    private String name;
    private int cidr;

    public NetworkAvailabilitySpecification(IP address, String name, int cidr) {
        this.address = address;
        this.name = name;
        this.cidr = cidr;
    }

    @Override
    public boolean isSatisfiedBy(Router router) {
        return router != null && isNetworkAvailable(router);
    }

    private boolean isNetworkAvailable(Router router) {
        var availability = true;
        for (Network network : router.retrieveNetworks()){
            if (network.getAddress().equals(address) && network.getCidr() == cidr){
                availability = false;
                break;
            }
        }

        return availability;
    }
}
