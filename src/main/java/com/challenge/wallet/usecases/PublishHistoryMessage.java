package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastHistoryProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishHistoryMessage {

  private final BroadcastHistoryProducer broadcastHistoryProducer;

  public void execute( final History history) {
    broadcastHistoryProducer.send(history);
  }
}