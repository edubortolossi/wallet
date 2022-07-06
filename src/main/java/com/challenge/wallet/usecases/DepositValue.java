package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.enums.Operations;
import com.challenge.wallet.exceptions.NotFoundException;
import com.challenge.wallet.gateways.AccountDataGateway;
import com.challenge.wallet.utils.JsonUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositValue {

  private final AccountDataGateway accountDataGateway;

  private final PublishHistoricMessage publishHistoricMessage;

  private final JsonUtils jsonUtils;

  public void execute(final Account account) {
    var accountToDeposit = getAccount(account.getCpf(), account.getAccountNumber());
    sumValueToAccount(account, accountToDeposit);

    accountDataGateway.save(accountToDeposit);

    publishHistoricMessage.execute(Historic.builder()
        .cpf(account.getCpf())
        .accountNumber(account.getAccountNumber())
        .payload(jsonUtils.toJson(account))
        .operation(Operations.DEPOSIT.name()).build());
  }

  private Account getAccount(final String cpf, final String accountNumber) {
    Optional<Account> accountOptional = accountDataGateway.findById(cpf, accountNumber);
    if(accountOptional.isEmpty()) {
      throw new NotFoundException("Account not found");
    } else {
      return accountOptional.get();
    }
  }

  private void sumValueToAccount(Account account, Account accountToDeposit) {
    accountToDeposit.setValue(accountToDeposit.getValue() + account.getValue());
  }
}
