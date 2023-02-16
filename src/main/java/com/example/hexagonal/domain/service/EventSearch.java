package com.example.hexagonal.domain.service;

import com.example.hexagonal.domain.entity.Event;
import com.example.hexagonal.domain.vo.ParsePolicyType;
import lombok.var;

import java.util.ArrayList;
import java.util.List;

public class EventSearch {

    public List<Event> retrieveEvents(List<String> unparsedEvents, ParsePolicyType policyType){
        var parsedEvents = new ArrayList<Event>();
        unparsedEvents.forEach(event ->{
            parsedEvents.add(Event.parsedEvent(event, policyType));
        });
        return parsedEvents;
    }
}
