package com.challenge.wallet.gateways.outputs.mongodb.repositories;


import com.challenge.wallet.gateways.outputs.mongodb.documents.HistoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends
    MongoRepository<HistoryDocument, String> {
}
