package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.HistoricDataGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAllHistoric {

  private final HistoricDataGateway historicDataGateway;

  public List<Historic> execute() {
    return historicDataGateway.findAll();
  }
}
