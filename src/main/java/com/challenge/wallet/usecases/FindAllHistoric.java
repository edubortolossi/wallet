package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.gateways.HistoricDataGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAllHistoric {

  private final HistoricDataGateway historicDataGateway;

  public List<History> execute() {
    return historicDataGateway.findAll();
  }
}
