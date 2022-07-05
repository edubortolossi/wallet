package com.challenge.wallet.gateways.outputs.mongodb.repositories;


import com.challenge.wallet.gateways.outputs.mongodb.documents.AccountDocument;
import com.challenge.wallet.gateways.outputs.mongodb.documents.AccountDocumentId;
import com.challenge.wallet.gateways.outputs.mongodb.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends
    MongoRepository<AccountDocument, AccountDocumentId> {
}
