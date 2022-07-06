package com.challenge.wallet.gateways.outputs.kafka;

import com.challenge.wallet.configurations.kafka.TopicProperties;
import com.challenge.wallet.domains.Historic;
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
public class BroadcastHistoricProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final TopicProperties topicProperties;
  private final JsonUtils jsonUtils;

  public void send(final Historic historic) {
    final var message = jsonUtils.toJson(historic);
    final var topic = topicProperties.getHistoricBroadcast();
    final var partitionKey = historic.getCpf()+"_"+ historic.getAccountNumber();

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
            topicProperties.getHistoricBroadcast(),
            key,
            message);
  }

  private FailureCallback failureCallback(final String message, final String key) {
    return ex -> {
      log.error(
          "Error sent to topic: {} , key: {}, message: {}",
          topicProperties.getHistoricBroadcast(),
          key,
          message);
    };
  }

}
