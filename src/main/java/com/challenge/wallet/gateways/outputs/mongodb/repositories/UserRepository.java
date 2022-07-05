package com.challenge.wallet.gateways.outputs.mongodb.repositories;


import com.challenge.wallet.gateways.outputs.mongodb.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends
    MongoRepository<UserDocument, String> {
}
