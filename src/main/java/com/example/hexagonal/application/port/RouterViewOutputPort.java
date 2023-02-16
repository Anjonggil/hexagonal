package com.example.hexagonal.application.port;

import com.example.hexagonal.domain.entity.Router;

import java.util.List;

public interface RouterViewOutputPort {
    List<Router> fetchRouters();
}
