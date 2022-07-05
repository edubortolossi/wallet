package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.History;
import com.challenge.wallet.domains.TransferValue;
import com.challenge.wallet.enums.Operations;
import com.challenge.wallet.exceptions.BusinessException;
import com.challenge.wallet.exceptions.NotFoundException;
import com.challenge.wallet.gateways.AccountDataGateway;
import com.challenge.wallet.utils.JsonUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferValueAccount {

  private final AccountDataGateway accountDataGateway;

  private final PublishHistoryMessage publishHistoryMessage;

  private final JsonUtils jsonUtils;

  public void execute(final TransferValue transferValue) {
    var value = transferValue.getValue();
    Account accountFrom = getAccount(transferValue.getCpfFrom(), transferValue.getAccountFrom());
    Account accountTo = getAccount(transferValue.getCpfTo(), transferValue.getAccountTo());

    subtractValueFrom(accountFrom, value);
    sumValueTo(accountTo, value);

    accountDataGateway.save(accountFrom);
    accountDataGateway.save(accountTo);

    publishHistoryMessage.execute(History.builder()
        .cpf(accountFrom.getCpf())
        .accountNumber(accountFrom.getAccountNumber())
        .payload(jsonUtils.toJson(transferValue))
        .operation(Operations.TRANSFER_VALUE.name()).build());
  }

  private void sumValueTo(final Account accountTo, final long value) {
    var totalValue = accountTo.getValue() + value;
    accountTo.setValue(totalValue);
  }

  private void subtractValueFrom(final Account accountFrom, final long value) {
    if(accountFrom.getValue() <= 0 || accountFrom.getValue() < value) {
      throw new BusinessException("Without balance");
    }
    var totalValue = accountFrom.getValue() - value;
    accountFrom.setValue(totalValue);
  }

  private Account getAccount(final String cpf, final String accountNumber) {
    Optional<Account> accountOptional = accountDataGateway.findById(cpf, accountNumber);
    if(accountOptional.isEmpty()) {
      throw new NotFoundException("Account not found");
    } else {
      return accountOptional.get();
    }
  }

}
