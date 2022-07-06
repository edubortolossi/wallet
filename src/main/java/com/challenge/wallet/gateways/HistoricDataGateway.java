package com.challenge.wallet.gateways;


import com.challenge.wallet.domains.Historic;
import java.util.List;

public interface HistoricDataGateway {
  void save(String cpf, String accountNumber, String payload, String operation);

  List<Historic> findAll();

  List<Historic> findByCpfAndAccountNumber(String cpf, String accountNumber);
}
