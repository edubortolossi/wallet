package com.challenge.wallet.gateways.inputs.kafka.resources;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.domains.TransferValue;
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

  public History toDomain() {
    return History.builder()
        .cpf(cpf)
        .accountNumber(accountNumber)
        .payload(payload)
        .operation(operation)
        .build();
  }
}
