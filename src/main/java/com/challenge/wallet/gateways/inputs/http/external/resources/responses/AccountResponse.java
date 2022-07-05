package com.challenge.wallet.gateways.inputs.http.external.resources.responses;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

  private String cpf;

  private String accountNumber;

  private long value;

  public AccountResponse(final Account account) {
    this.cpf = account.getCpf();
    this.accountNumber = account.getAccountNumber();
    this.value = account.getValue();
  }
}
