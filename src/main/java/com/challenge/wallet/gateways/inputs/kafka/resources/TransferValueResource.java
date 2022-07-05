package com.challenge.wallet.gateways.inputs.kafka.resources;

import com.challenge.wallet.domains.TransferValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferValueResource {
  private String cpfFrom;
  private String accountFrom;
  private String cpfTo;
  private String accountTo;
  private long value;

  public TransferValue toDomain() {
    return TransferValue.builder()
        .cpfFrom(cpfFrom)
        .accountFrom(accountFrom)
        .cpfTo(cpfTo)
        .accountTo(accountTo)
        .value(value)
        .build();
  }
}
