package com.challenge.wallet.gateways.inputs.http.external.resources.request;

import com.challenge.wallet.domains.TransferValue;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequest {

  @NotNull
  private String cpfFrom;

  @NotNull
  private String accountFrom;

  @NotNull
  private String cpfTo;

  @NotNull
  private String accountTo;

  @NotNull
  private long value;

  public TransferValue toDomain() {
    return TransferValue.builder()
        .cpfFrom(cpfFrom)
        .cpfTo(cpfTo)
        .accountFrom(accountFrom)
        .accountTo(accountTo)
        .value(value)
        .build();
  }
}
