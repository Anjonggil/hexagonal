package com.example.hexagonal.framework.adapter.out.h2.mapper;

import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.entity.Switch;
import com.example.hexagonal.domain.vo.*;
import com.example.hexagonal.framework.adapter.out.h2.data.*;
import lombok.var;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RouterH2Mapper {

    public static Router toDomain(RouterData routerData) {
        var routerType = RouterType.valueOf(routerData.getRouterType().name());
        var routerId = RouterId.withId(routerData.getRouterId().toString());
        var switchId = SwitchId.withId(routerData.getNetworkSwitch().getSwitchId().toString());
        var switchType = SwitchType.valueOf(routerData.getNetworkSwitch().getSwitchType().name());
        var ip = IP.fromAddress(routerData.getNetworkSwitch().getIp().getAddress());
        var networks = getNetworksFromData(routerData.getNetworkSwitch().getNetworks());

        var networkSwitch = new Switch(switchType, switchId, networks, ip);
        return new Router(routerType, routerId, networkSwitch);
    }

    public static RouterData toH2(Router router){
        var routerTypeData = RouterTypeData.valueOf(router.getRouterType().toString());
        var routerId = router.getRouterId().getId();
        var switchId = router.getNetworkSwitch().getSwitchId().getId();
        var switchTypeData = SwitchTypeData.valueOf(router.getNetworkSwitch().getType().toString());
        var ipData = IPData.fromAddress(router.getNetworkSwitch().getAddress().getAddress());
        var networkDataList = getNetworksFromDomain(router.retrieveNetworks(), routerId);

        var switchData = new SwitchData(
                routerId,
                switchId,
                switchTypeData,
                networkDataList,
                ipData);
        return new RouterData(routerId, routerTypeData, switchData);
    }

    private static List<NetworkData> getNetworksFromDomain(List<Network> networks, UUID switchId) {
        List<NetworkData> networkDataList = new ArrayList<>();
        networks.forEach(network ->{
            var networkData = new NetworkData(
                    switchId,
                    IPData.fromAddress(network.getAddress().getAddress()),
                    network.getName(),
                    network.getCidr()
            );
            networkDataList.add(networkData);
        });
        return networkDataList;
    }


    private static List<Network> getNetworksFromData(List<NetworkData> networkData) {
        List<Network> networks = new ArrayList<>();
        networkData.forEach(data -> {
            Network network = null;
            try {
                network = new Network(
                        IP.fromAddress(data.getIp().getAddress()),
                        data.getName(),
                        data.getCidr());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            networks.add(network);
        });
        return networks;
    }
}
