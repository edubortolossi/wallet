package com.challenge.wallet.gateways.outputs.mongodb;

import com.challenge.wallet.domains.History;
import com.challenge.wallet.gateways.HistoryDataGateway;
import com.challenge.wallet.gateways.outputs.mongodb.documents.HistoryDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.HistoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistoryDataGatewayMongoImpl implements HistoryDataGateway {

  private final HistoryRepository historyRepository;

  @Override
  public void save(String cpf, String accountNumber, String payload, String operation) {
    historyRepository.save(new HistoryDocument(cpf, accountNumber, payload, operation));
  }

  @Override
  public List<History> findAll() {
    return historyRepository.findAll().stream()
        .map(HistoryDocument::toDomain).collect(Collectors.toList());
  }
}
