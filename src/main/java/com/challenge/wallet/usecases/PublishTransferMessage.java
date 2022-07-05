package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.TransferValue;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastTransferProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishTransferMessage {

  private final BroadcastTransferProducer broadcastKafkaProducer;

  public void execute( final TransferValue transferValue) {
    broadcastKafkaProducer.send(transferValue);
  }
}
