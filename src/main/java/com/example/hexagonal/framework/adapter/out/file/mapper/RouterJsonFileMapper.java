package com.example.hexagonal.framework.adapter.out.file.mapper;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.entity.Switch;
import com.example.hexagonal.domain.vo.*;
import com.example.hexagonal.framework.adapter.out.file.json.*;
import lombok.var;

import java.util.ArrayList;
import java.util.List;

public class RouterJsonFileMapper {
    public static Router toDomain(RouterJson routerJson){
        var routerId = RouterId.withId(routerJson.getRouterId().toString());
        var routerType = RouterType.valueOf(routerJson.getRouterType().name());
        var switchId = SwitchId.withId(routerJson.getNetworkSwitch().getSwitchId().toString());
        var switchType = SwitchType.valueOf(routerJson.getNetworkSwitch().getSwitchType().toString());
        var ip = IP.fromAddress(routerJson.getNetworkSwitch().getIp().getAddress());
        var networks =  getNetworksFromJson(routerJson.getNetworkSwitch().getNetworks());

        var networkSwitch = new Switch(switchType, switchId, networks, ip);

        return new Router(routerType, routerId, networkSwitch);
    }

    public static RouterJson toJson(Router router){
        var routerId = router.getRouterId().getId();
        var routerTypeJson = RouterTypeJson.valueOf(router.getRouterType().toString());
        var switchIdJson = router.getNetworkSwitch().getSwitchId().getId();
        var switchTypeJson = SwitchTypeJson.valueOf(router.getNetworkSwitch().getType().toString());
        var ipJson = IPJson.fromAddress(router.getNetworkSwitch().getAddress().getAddress());
        var networkDataList = getNetworksFromDomain(router.retrieveNetworks());

        var switchJson = new SwitchJson(switchIdJson, ipJson, switchTypeJson, networkDataList);

        return new RouterJson(routerId, routerTypeJson, switchJson);
    }

    private static List<Network> getNetworksFromJson(List<NetworkJson> networkJson){
        List<Network> networks = new ArrayList<>();
        networkJson.forEach(json ->{
            Network network = null;
            try {
                network = new Network(
                        IP.fromAddress(json.getIp().getAddress()),
                        json.getNetworkName(),
                        Integer.valueOf(json.getCidr()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            networks.add(network);
        });
        return networks;
    }

    private static List<NetworkJson> getNetworksFromDomain(List<Network> networks){
        List<NetworkJson> networkJsonList = new ArrayList<>();
        networks.forEach(network -> {
            var networkJson = new NetworkJson(
                    IPJson.fromAddress(network.getAddress().getAddress()),
                    network.getName(),
                    String.valueOf(network.getCidr())
            );
            networkJsonList.add(networkJson);
        });
        return networkJsonList;
    }

}
