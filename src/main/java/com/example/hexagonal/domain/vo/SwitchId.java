package com.example.hexagonal.domain.vo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SwitchId {

    private final UUID id;

    private SwitchId(UUID id){
        this.id = id;
    }

    public static SwitchId withId(String id){
        return new SwitchId(UUID.fromString(id));
    }

    public  static SwitchId withoutId(){
        return new SwitchId(UUID.randomUUID());
    }
}
