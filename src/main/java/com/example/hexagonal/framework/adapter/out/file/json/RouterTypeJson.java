package com.example.hexagonal.framework.adapter.out.file.json;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public enum RouterTypeJson {
    EDGE,CORE;
}
