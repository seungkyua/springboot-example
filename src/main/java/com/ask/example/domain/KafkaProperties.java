package com.ask.example.domain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "example.kafka")
public class KafkaProperties {

    private List<String> bootstrapServers;
    private Integer ackLevel;
    private Topic topic;

    @ToString
    @Getter
    @Setter
    public static class Topic {
        private String checkout;
        private String reservation;
    }
}
