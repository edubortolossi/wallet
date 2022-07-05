package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.domains.User;
import com.challenge.wallet.gateways.HistoryDataGateway;
import com.challenge.wallet.gateways.UserDataGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAllHistoric {

  private final HistoryDataGateway historyDataGateway;

  public List<History> execute() {
    return historyDataGateway.findAll();
  }
}
