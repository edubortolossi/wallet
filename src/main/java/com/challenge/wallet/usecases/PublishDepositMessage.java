package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastDepositProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishDepositMessage {

  private final BroadcastDepositProducer broadcastDepositProducer;

  public void execute( final Account account) {
    broadcastDepositProducer.send(account);
  }
}
