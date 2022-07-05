package com.challenge.wallet.gateways.outputs.kafka;

import com.challenge.wallet.configurations.kafka.TopicProperties;
import com.challenge.wallet.domains.TransferValue;
import com.challenge.wallet.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class BroadcastTransferProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final TopicProperties topicProperties;
  private final JsonUtils jsonUtils;

  public void send(final TransferValue transferValue) {
    final var message = jsonUtils.toJson(transferValue);
    final var topic = topicProperties.getTransferValueBroadcast();
    final var partitionKey = transferValue.getCpfFrom()+"_"+transferValue.getCpfFrom();

    kafkaTemplate
        .send(topic, partitionKey, message)
        .addCallback(successCallback(message, partitionKey),
            failureCallback(message, partitionKey));
  }

  private SuccessCallback<SendResult<String, String>> successCallback(
      final String message, final String key) {
    return result ->
        log.debug(
            "Message sent to topic: {} , key: {}, message: {}",
            topicProperties.getTransferValueBroadcast(),
            key,
            message);
  }

  private FailureCallback failureCallback(final String message, final String key) {
    return ex -> {
      log.error(
          "Error sent to topic: {} , key: {}, message: {}",
          topicProperties.getTransferValueBroadcast(),
          key,
          message);
    };
  }

}