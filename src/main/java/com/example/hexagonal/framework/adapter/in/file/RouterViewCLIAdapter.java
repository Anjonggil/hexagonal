package com.example.hexagonal.framework.adapter.in.file;

import com.example.hexagonal.application.port.RouterViewInputPort;
import com.example.hexagonal.application.usecase.RouterViewUseCase;
import com.example.hexagonal.domain.entity.Router;
import com.example.hexagonal.domain.vo.RouterType;
import com.example.hexagonal.framework.adapter.out.file.RouterViewFileAdapter;

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
