package com.challenge.wallet.gateways.inputs.kafka;


import com.challenge.wallet.configurations.kafka.TopicProperties;
import com.challenge.wallet.gateways.inputs.kafka.resources.TransferValueResource;
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
public class BroadcastTransferListener {

  private final TopicProperties topicProperties;
  private final JsonUtils jsonUtils;
  private final TransferValueAccount transferValueAccount;

  @KafkaListener(
      topics = "${spring.kafka.topics.transferValueBroadcast}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listener(
      @Payload final String message,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) final String key,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) final String partitionId,
      @Header(KafkaHeaders.OFFSET) final String offset) {
    log.debug(
        "Message received from topic {} partitionId {} offset {} key {} message {}",
        topicProperties.getTransferValueBroadcast(),
        partitionId,
        offset,
        key,
        message);
    try {
      final var resource = jsonUtils.toObject(message, TransferValueResource.class);
      transferValueAccount.execute(resource.toDomain());
    } catch (final Exception ex) {
      log.error(
          "Kafka Listener {} has failed for message {}.",
          topicProperties.getTransferValueBroadcast(),
          message,
          ex);
    }
  }
}
