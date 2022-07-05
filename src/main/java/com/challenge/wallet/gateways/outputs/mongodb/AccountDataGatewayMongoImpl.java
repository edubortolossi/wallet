package com.challenge.wallet.gateways.outputs.mongodb;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.User;
import com.challenge.wallet.gateways.AccountDataGateway;
import com.challenge.wallet.gateways.UserDataGateway;
import com.challenge.wallet.gateways.outputs.mongodb.documents.AccountDocument;
import com.challenge.wallet.gateways.outputs.mongodb.documents.AccountDocumentId;
import com.challenge.wallet.gateways.outputs.mongodb.documents.UserDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.AccountRepository;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountDataGatewayMongoImpl implements AccountDataGateway {

  private final AccountRepository accountRepository;

  @Override
  public Account save(Account account) {
    return accountRepository.save(new AccountDocument(account)).toDomain();
  }

  @Override
  public List<Account> findAll() {
    return accountRepository.findAll().stream()
        .map(AccountDocument::toDomain).collect(Collectors.toList());
  }

  @Override
  public Optional<Account> findById(String cpf, String accountNumber) {
    return accountRepository.findById(new AccountDocumentId(cpf, accountNumber))
        .map(AccountDocument::toDomain);
  }
}
