package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.exceptions.ConflictException;
import com.challenge.wallet.exceptions.NotFoundException;
import com.challenge.wallet.gateways.AccountDataGateway;
import com.challenge.wallet.gateways.UserDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveAccount {

  private final UserDataGateway userDataGateway;
  private final AccountDataGateway accountDataGateway;

  public Account execute(final Account account) {
    validateUserExists(account.getCpf());
    validateAccountExists(account);
    account.setValue(0L);
    return accountDataGateway.save(account);
    }

  private void validateAccountExists(Account account) {
    if(accountDataGateway.findById(account.getCpf(), account.getAccountNumber()).isPresent()) {
      throw new ConflictException("There is already an account with this number");
    }
  }

  private void validateUserExists(String cpf) {
      if(userDataGateway.findById(cpf).isEmpty()) {
        throw new NotFoundException("Account opening user does not exist");
      }
    }

}
