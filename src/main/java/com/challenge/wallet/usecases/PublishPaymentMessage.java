package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Payment;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastPaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishPaymentMessage {

  private final BroadcastPaymentProducer broadcastPaymentProducer;

  public void execute( final Payment payment) {
    broadcastPaymentProducer.send(payment);
  }
}
