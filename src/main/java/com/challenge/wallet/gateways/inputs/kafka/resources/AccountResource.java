package com.challenge.wallet.gateways.inputs.kafka.resources;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.TransferValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResource {
  private String cpf;
  private String accountNumber;
  private long value;

  public Account toDomain() {
    return Account.builder()
        .cpf(cpf)
        .accountNumber(accountNumber)
        .value(value)
        .build();
  }
}
