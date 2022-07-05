package com.challenge.wallet.gateways.inputs.kafka;


import com.challenge.wallet.configurations.kafka.TopicProperties;
import com.challenge.wallet.domains.History;
import com.challenge.wallet.gateways.inputs.kafka.resources.HistoryResource;
import com.challenge.wallet.gateways.inputs.kafka.resources.TransferValueResource;
import com.challenge.wallet.usecases.ProcessHistory;
import com.challenge.wallet.usecases.TransferValueAccount;
import com.challenge.wallet.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BroadcastHistoryListener {

  private final TopicProperties topicProperties;
  private final JsonUtils jsonUtils;
  private final ProcessHistory processHistory;

  @KafkaListener(
      topics = "${spring.kafka.topics.historyBroadcast}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listener(
      @Payload final String message,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) final String key,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) final String partitionId,
      @Header(KafkaHeaders.OFFSET) final String offset) {
    log.debug(
        "Message received from topic {} partitionId {} offset {} key {} message {}",
        topicProperties.getHistoryBroadcast(),
        partitionId,
        offset,
        key,
        message);
    try {
      final var resource = jsonUtils.toObject(message, HistoryResource.class);
      processHistory.execute(resource.toDomain());
    } catch (final Exception ex) {
      log.error(
          "Kafka Listener {} has failed for message {}.",
          topicProperties.getHistoryBroadcast(),
          message,
          ex);
    }
  }
}
