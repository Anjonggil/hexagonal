package com.example.hexagonal.domain.vo;

public class Network {

    private final IP address;

    private final String name;

    private final int cidr;

    public Network(IP address, String name, int cidr) throws IllegalAccessException {
        if (cidr < 1 || cidr > 32){
            throw new IllegalAccessException("Invalid CIDR Value!");
        }
        this.address = address;
        this.name = name;
        this.cidr = cidr;
    }

    public IP getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getCidr() {
        return cidr;
    }
}
