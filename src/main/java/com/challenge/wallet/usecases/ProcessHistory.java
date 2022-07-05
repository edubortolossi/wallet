package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.gateways.HistoryDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessHistory {

  private final HistoryDataGateway historyDataGateway;

  public void execute(final History history) {
    historyDataGateway.save(history.getCpf(), history.getAccountNumber(), history.getPayload(), history.getOperation());
  }
}
