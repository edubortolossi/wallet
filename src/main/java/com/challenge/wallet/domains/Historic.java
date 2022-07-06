package com.challenge.wallet.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Historic {
  private String cpf;
  private String accountNumber;
  private String payload;
  private String operation;
}
