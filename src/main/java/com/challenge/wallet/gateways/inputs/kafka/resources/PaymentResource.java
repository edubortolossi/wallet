package com.challenge.wallet.gateways.inputs.kafka.resources;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.Payment;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResource {
  private String cpf;

  private String accountNumber;

  private String barCode;

  private String description;

  @NotNull
  private long value;
  public Payment toDomain() {
    return Payment.builder()
        .cpf(cpf)
        .accountNumber(accountNumber)
        .barCode(barCode)
        .description(description)
        .value(value)
        .build();
  }
}
