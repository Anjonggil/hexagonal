package com.example.hexagonal.domain.vo;

import lombok.Getter;

@Getter
public class IP {

    private final String address;

    private final Protocol protocol;

    private IP(String address){
        if (address == null){
            throw new IllegalArgumentException("Null IP address");
        }

        if (address.length()<= 15){
            this.protocol = Protocol.IPV4;
        } else {
            this.protocol = Protocol.IPV6;
        }
        this.address = address;
    }

    public static IP fromAddress(String address) {
        return new IP(address);
    }
}
