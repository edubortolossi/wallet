package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.History;
import com.challenge.wallet.domains.Payment;
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
public class BillsPayment {

  private final AccountDataGateway accountDataGateway;

  private final PublishHistoryMessage publishHistoryMessage;

  private final JsonUtils jsonUtils;

  public void execute(final Payment payment) {
    var accountToDeposit = getAccount(payment.getCpf(), payment.getAccountNumber());
    sumValueToAccount(payment, accountToDeposit);
    accountDataGateway.save(accountToDeposit);

    publishHistoryMessage.execute(History.builder()
        .cpf(payment.getCpf())
        .accountNumber(payment.getAccountNumber())
        .payload(jsonUtils.toJson(payment))
        .operation(Operations.PAYMENT.name()).build());
  }

  private Account getAccount(final String cpf, final String accountNumber) {
    Optional<Account> accountOptional = accountDataGateway.findById(cpf, accountNumber);
    if(accountOptional.isEmpty()) {
      throw new NotFoundException("Account not found");
    } else {
      return accountOptional.get();
    }
  }

  private void sumValueToAccount(Payment payment, Account account) {
    if(account.getValue() < payment.getValue() ) {
      throw new BusinessException("Without balance");
    }
    account.setValue(account.getValue() - payment.getValue());
  }
}
