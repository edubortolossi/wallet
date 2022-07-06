package com.challenge.wallet.gateways.outputs.mongodb.documents;

import com.challenge.wallet.domains.Historic;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("historic")
public class HistoricDocument {

  @Id
  private String id;

  private String cpf;

  private String accountNumber;

  private String payload;

  private String operation;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public HistoricDocument(String cpf, String accountNumber, String payload, String operation) {
    this.cpf = cpf;
    this.accountNumber = accountNumber;
    this.payload = payload;
    this.operation = operation;
  }

  public Historic toDomain() {
    return Historic.builder()
        .accountNumber(accountNumber)
        .cpf(cpf)
        .operation(operation)
        .payload(payload)
        .build();
  }
}
