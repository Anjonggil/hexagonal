package com.example.hexagonal.domain.specification.share;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    Specification<T> and(Specification<T> specification);
}
