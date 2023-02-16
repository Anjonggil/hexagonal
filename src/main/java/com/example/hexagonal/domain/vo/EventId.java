package com.example.hexagonal.domain.vo;

public class EventId {
    private final String id;

    private EventId(String id){
        this.id = id;
    }

    public static EventId of(String id){
        return new EventId(id);
    }

}