package com.ask.example.controller;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ClientInfo {

    private final String channel;
    private final String clientAddress;

    public ClientInfo(String channel, String clientAddress) {
        this.channel = channel;
        this.clientAddress = clientAddress;
    }
}
