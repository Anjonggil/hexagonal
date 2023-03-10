package com.example.hexagonal.domain.entity;

import com.example.hexagonal.domain.vo.IP;
import com.example.hexagonal.domain.vo.Network;
import com.example.hexagonal.domain.vo.RouterId;
import com.example.hexagonal.domain.vo.RouterType;
import lombok.Getter;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class Router {

    private final RouterType routerType;

    private final RouterId routerId;

    private Switch networkSwitch;

    public Router(RouterType routerType, RouterId routerId, Switch networkSwitch) {
        this.routerType = routerType;
        this.routerId = routerId;
        this.networkSwitch = networkSwitch;
    }

    public Router(RouterType routerType, RouterId routerId) {
        this.routerType = routerType;
        this.routerId = routerId;
    }

    public static Predicate<Router> filterRouterByType(RouterType routerType){
        return routerType.equals(RouterType.CORE)
                ? isCore() : isEdge();
    }

    public static Predicate<Router> isCore(){
        return p -> p.getRouterType() == RouterType.CORE;
    }

    public static Predicate<Router> isEdge(){
        return p -> p.getRouterType() == RouterType.EDGE;
    }

    public void addNetworkToSwitch(Network network){
        this.networkSwitch = networkSwitch.addNetwork(network);
    }
    public Network createNetwork(IP address, String name, int cidr) throws IllegalAccessException {
        return new Network(address, name, cidr);
    }

    public List<Network> retrieveNetworks(){
        return networkSwitch.getNetworks();
    }

    public RouterType getRouterType() {
        return routerType;
    }
}
