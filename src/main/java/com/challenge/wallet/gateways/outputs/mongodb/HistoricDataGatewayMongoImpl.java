package com.challenge.wallet.gateways.outputs.mongodb;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.gateways.HistoricDataGateway;
import com.challenge.wallet.gateways.outputs.mongodb.documents.HistoricDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.HistoricRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistoricDataGatewayMongoImpl implements HistoricDataGateway {

  private final HistoricRepository historicRepository;

  @Override
  public void save(String cpf, String accountNumber, String payload, String operation) {
    historicRepository.save(new HistoricDocument(cpf, accountNumber, payload, operation));
  }

  @Override
  public List<History> findAll() {
    return historicRepository.findAll().stream()
        .map(HistoricDocument::toDomain).collect(Collectors.toList());
  }
}
