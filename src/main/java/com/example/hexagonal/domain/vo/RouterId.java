package com.example.hexagonal.domain.vo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RouterId {

    private final UUID id;

    public RouterId(UUID id) {
        this.id = id;
    }

    public static RouterId withId(String id){
        return new RouterId(UUID.fromString(id));
    }

    public static RouterId withoutId(){
        return new RouterId(UUID.randomUUID());
    }
}
