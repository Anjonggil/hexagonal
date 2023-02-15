package com.example.hexagonal.framework.adapter;

import com.example.hexagonal.application.port.RouterViewInputPort;
import com.example.hexagonal.application.usecase.RouterViewUseCase;
import com.example.hexagonal.domain.Router;
import com.example.hexagonal.domain.RouterType;

import java.util.List;

public class RouterViewCLIAdapter {

    RouterViewUseCase routerViewUseCase;

    public RouterViewCLIAdapter(){
        setAdapter();
    }

    private void setAdapter() {
        this.routerViewUseCase = new RouterViewInputPort(RouterViewFileAdapter.getInstance());
    }

    public List<Router> obtainRelatedRouters(String type){
        return routerViewUseCase.getRouters(Router.filterRouterByType(RouterType.valueOf(type)));
    }
}
