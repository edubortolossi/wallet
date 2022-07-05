package com.challenge.wallet.configurations.kafka;

import static org.springframework.kafka.listener.ContainerProperties.AckMode.RECORD;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

  private final KafkaProperties kafkaProperties;

  @Bean("fbInventoryListenerContainerFactory")
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
  fbInventoryListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.setConcurrency(kafkaProperties.getListener().getConcurrency());
    factory.getContainerProperties().setAckMode(RECORD);
    var properties = kafkaProperties.buildConsumerProperties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaProperties.getBootstrapServers());
    factory.setConsumerFactory(
        new DefaultKafkaConsumerFactory<>(properties));
    return factory;
  }
}
