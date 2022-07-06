package com.challenge.wallet.configurations.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("spring.kafka.topics")
public class TopicProperties {

    private String transferValueBroadcast;
    private String depositValueBroadcast;
    private String paymentBroadcast;
    private String historicBroadcast;
}
