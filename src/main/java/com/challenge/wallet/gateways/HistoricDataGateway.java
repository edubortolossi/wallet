package com.challenge.wallet.gateways;


import com.challenge.wallet.domains.History;
import java.util.List;

public interface HistoricDataGateway {
  void save(String cpf, String accountNumber, String payload, String operation);

  List<History> findAll();
}
