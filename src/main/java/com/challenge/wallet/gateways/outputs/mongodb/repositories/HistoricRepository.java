package com.challenge.wallet.gateways.outputs.mongodb.repositories;


import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.outputs.mongodb.documents.HistoricDocument;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricRepository extends
    MongoRepository<HistoricDocument, String> {

  List<Historic> findByCpfAndAccountNumber(String cpf, String accountNumber);
}
