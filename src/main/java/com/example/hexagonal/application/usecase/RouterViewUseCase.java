package com.example.hexagonal.application.usecase;

import com.example.hexagonal.domain.entity.Router;

import java.util.List;
import java.util.function.Predicate;

public interface RouterViewUseCase {
    List<Router> getRouters(Predicate<Router> filter);
}
