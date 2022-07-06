package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.HistoricDataGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindHistoricByCpfAndAccount {

  private final HistoricDataGateway historicDataGateway;

  public List<Historic> execute(final String cpf, final String accountNumber) {
    return historicDataGateway.findByCpfAndAccountNumber(cpf, accountNumber);
  }
}
