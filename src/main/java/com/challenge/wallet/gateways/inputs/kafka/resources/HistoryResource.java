package com.challenge.wallet.gateways.inputs.kafka.resources;

import com.challenge.wallet.domains.Historic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResource {
  private String cpf;
  private String accountNumber;
  private String payload;
  private String operation;

  public Historic toDomain() {
    return Historic.builder()
        .cpf(cpf)
        .accountNumber(accountNumber)
        .payload(payload)
        .operation(operation)
        .build();
  }
}
