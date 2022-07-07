package com.challenge.wallet.consumers;

import com.challenge.wallet.gateways.inputs.kafka.resources.AccountResource;
import com.challenge.wallet.gateways.inputs.kafka.resources.TransferValueResource;
import com.challenge.wallet.utils.JsonUtils;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
@Profile("container-test")
public class BroadcastTransferKafkaConsumer {

  private final JsonUtils jsonUtils;
  private TransferValueResource message = null;
  private String messageAsJson = null;
  private Set<TransferValueResource> messages = new HashSet<>();

  @KafkaListener(topics = "challenge.wallet.transfer.input")
  public void message(@Payload final String message) {
    this.messageAsJson = message;
    this.message = jsonUtils.toObject(message, TransferValueResource.class);
    this.messages.add(jsonUtils.toObject(message, TransferValueResource.class));
  }

  public void reset() {
    this.message = null;
    this.messages.clear();
  }
}
