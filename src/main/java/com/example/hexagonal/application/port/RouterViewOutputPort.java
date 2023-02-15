package com.example.hexagonal.application.port;

import com.example.hexagonal.domain.Router;

import java.util.List;

public interface RouterViewOutputPort {
    List<Router> fetchRouters();
}
