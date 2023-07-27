package com.ask.example.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PropertiesComponent {

    private final String exampleDomain;
    private final List<String> bootStrapServers;
    private final String checkoutTopic;
    private final String reservationTopic;
    private final Integer ackLevel;

    public PropertiesComponent(
            @Value("${example.domain.name:example.ask.com}") String exampleDomain,
            @Value("${example.kafka.bootstrap-servers}") List<String> bootStrapServers,
            @Value("${example.kafka.topic.checkout}") String checkoutTopic,
            @Value("${example.kafka.topic.reservation}") String reservationTopic,
            @Value("${example.kafka.ack-level}") Integer ackLevel
    ) {
        this.exampleDomain = exampleDomain;
        this.bootStrapServers = bootStrapServers;
        this.checkoutTopic = checkoutTopic;
        this.reservationTopic = reservationTopic;
        this.ackLevel = ackLevel;

        log.info(this.exampleDomain);
        log.info(this.bootStrapServers.toString());
        log.info(this.checkoutTopic);
        log.info(this.reservationTopic);
        log.info(String.valueOf(this.ackLevel));
    }
}
