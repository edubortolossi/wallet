package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.gateways.HistoricDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessHistoric {

  private final HistoricDataGateway historicDataGateway;

  public void execute(final History history) {
    historicDataGateway.save(history.getCpf(), history.getAccountNumber(), history.getPayload(), history.getOperation());
  }
}
