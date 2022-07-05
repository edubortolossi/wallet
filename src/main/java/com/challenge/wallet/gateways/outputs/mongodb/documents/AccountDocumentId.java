package com.challenge.wallet.gateways.outputs.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDocumentId {
  private String cpf;
  private String accountNumber;
}
