package com.challenge.wallet.gateways.inputs.http.external.resources.request;

import com.challenge.wallet.domains.Account;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountValueRequest {
  @NotNull
  private String cpf;

  @NotNull
  private String accountNumber;

  @NotNull
  private long value;

  public Account toDomain() {
    return Account.builder()
        .cpf(cpf)
        .accountNumber(accountNumber)
        .value(value)
        .build();
  }
}
