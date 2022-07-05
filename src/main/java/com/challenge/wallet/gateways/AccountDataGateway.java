package com.challenge.wallet.gateways;


import com.challenge.wallet.domains.Account;
import java.util.List;
import java.util.Optional;

public interface AccountDataGateway {
  Account save(final Account account);

  List<Account> findAll();

  Optional<Account> findById(final String cpf, final String accountNumber);
}
