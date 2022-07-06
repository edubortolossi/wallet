package com.challenge.wallet.gateways.outputs.mongodb.repositories;


import com.challenge.wallet.gateways.outputs.mongodb.documents.HistoricDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricRepository extends
    MongoRepository<HistoricDocument, String> {
}
