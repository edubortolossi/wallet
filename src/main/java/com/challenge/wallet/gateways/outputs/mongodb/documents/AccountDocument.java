package com.challenge.wallet.gateways.outputs.mongodb.documents;

import com.challenge.wallet.domains.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("account")
public class AccountDocument {
  @Id
  private AccountDocumentId accountDocumentId;
  private long value;

  public AccountDocument(final Account account) {
    this.accountDocumentId = new AccountDocumentId(account.getCpf(), account.getAccountNumber());
    this.value = account.getValue();
  }

  public Account toDomain() {
    return Account.builder()
        .cpf(this.accountDocumentId.getCpf())
        .accountNumber(this.accountDocumentId.getAccountNumber())
        .value(value)
        .build();
  }
}
