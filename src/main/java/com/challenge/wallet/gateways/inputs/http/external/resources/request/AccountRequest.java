package com.challenge.wallet.gateways.inputs.http.external.resources.request;

import com.challenge.wallet.domains.Account;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequest {

  @NotNull
  private String cpf;

  @NotNull
  private String accountNumber;

  public Account toDomain() {
    return Account.builder()
        .cpf(cpf)
        .accountNumber(accountNumber)
        .build();
  }
}
