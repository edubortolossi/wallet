package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.HistoricDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessHistoric {

  private final HistoricDataGateway historicDataGateway;

  public void execute(final Historic historic) {
    historicDataGateway.save(historic.getCpf(), historic.getAccountNumber(), historic.getPayload(), historic.getOperation());
  }
}
