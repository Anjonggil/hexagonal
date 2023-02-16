package com.example.hexagonal.domain.policy;

import com.example.hexagonal.domain.entity.Event;

import java.time.format.DateTimeFormatter;

public interface EventParser {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));

    Event parseEvent(String event);
}
