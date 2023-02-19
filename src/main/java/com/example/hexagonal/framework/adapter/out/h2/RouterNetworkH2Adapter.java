package com.example.hexagonal.framework.adapter.out.h2;

import com.example.hexagonal.application.port.RouterNetworkOutputPort;
import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.vo.RouterId;
import com.example.hexagonal.framework.adapter.out.h2.data.RouterData;
import com.example.hexagonal.framework.adapter.out.h2.mapper.RouterH2Mapper;
import lombok.var;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class RouterNetworkH2Adapter implements RouterNetworkOutputPort {

    private static RouterNetworkH2Adapter instance;

    @PersistenceContext
    private EntityManager em;

    private RouterNetworkH2Adapter() {
        setUpH2Database();
    }

    private void setUpH2Database() {
        var entityManagerFactory = Persistence.createEntityManagerFactory("inventory");
        var em = entityManagerFactory.createEntityManager();
        this.em = em;
    }

    @Override
    public boolean persistRouter(Router router) {
        var routerData = RouterH2Mapper.toH2(router);
        em.persist(routerData);
        return true;
    }

    @Override
    public Router fetchRouterById(RouterId routerId) {
        var routerData = em.getReference(RouterData.class, routerId.getId());
        return RouterH2Mapper.toDomain(routerData);
    }

    public static RouterNetworkH2Adapter getInstance(){
        if (instance == null){
            instance = new RouterNetworkH2Adapter();
        }

        return instance;
    }
}
