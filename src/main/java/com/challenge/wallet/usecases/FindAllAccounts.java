package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.gateways.AccountDataGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAllAccounts {

  private final AccountDataGateway accountDataGateway;

  public List<Account> execute() {
    return accountDataGateway.findAll();
  }
}
