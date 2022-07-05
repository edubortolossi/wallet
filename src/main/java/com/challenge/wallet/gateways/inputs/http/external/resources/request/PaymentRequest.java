package com.challenge.wallet.gateways.inputs.http.external.resources.request;

import com.challenge.wallet.domains.Payment;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

  @NotNull
  private String cpf;

  @NotNull
  private String accountNumber;

  @NotNull
  private String barCode;

  private String description;

  @NotNull
  private long value;

  public Payment toDomain() {
    return Payment.builder()
        .cpf(cpf)
        .accountNumber(accountNumber)
        .description(description)
        .barCode(barCode)
        .value(value)
        .build();
  }
}
