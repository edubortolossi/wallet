package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.Historic;
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
public class WithdrawValue {

  private final AccountDataGateway accountDataGateway;

  private final PublishHistoryMessage publishHistoryMessage;

  private final JsonUtils jsonUtils;

  public Account execute(final Account account) {
    var accountToWithdraw = getAccount(account.getCpf(), account.getAccountNumber());
    subtractValueToAccount(account, accountToWithdraw);

    publishHistoryMessage.execute(Historic.builder()
        .cpf(account.getCpf())
        .accountNumber(account.getAccountNumber())
        .payload(jsonUtils.toJson(account))
        .operation(Operations.WITHDRAW.name()).build());

    return accountDataGateway.save(accountToWithdraw);
  }

  private void subtractValueToAccount(Account account, Account accountToWithdraw) {
    if(accountToWithdraw.getValue() < account.getValue() ) {
      throw new BusinessException("Without balance");
    }
    accountToWithdraw.setValue(accountToWithdraw.getValue() -  account.getValue());
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
