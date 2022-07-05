package com.challenge.wallet.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferValue {

  private String cpfFrom;
  private String accountFrom;
  private String cpfTo;
  private String accountTo;
  private long value;

}
