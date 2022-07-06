package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastHistoricProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishHistoricMessage {

  private final BroadcastHistoricProducer broadcastHistoricProducer;

  public void execute( final Historic historic) {
    broadcastHistoricProducer.send(historic);
  }
}
