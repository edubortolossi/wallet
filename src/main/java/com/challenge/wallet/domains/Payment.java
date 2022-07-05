package com.challenge.wallet.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  private String cpf;
  private String accountNumber;
  private String barCode;
  private String description;
  private long value;
}
