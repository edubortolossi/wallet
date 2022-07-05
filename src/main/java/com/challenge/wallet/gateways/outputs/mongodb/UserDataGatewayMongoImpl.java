package com.challenge.wallet.gateways.outputs.mongodb;

import com.challenge.wallet.domains.User;
import com.challenge.wallet.gateways.UserDataGateway;
import com.challenge.wallet.gateways.outputs.mongodb.documents.UserDocument;
import com.challenge.wallet.gateways.outputs.mongodb.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataGatewayMongoImpl implements UserDataGateway {

  private final UserRepository userRepository;

  public User save(final User user) {
    return userRepository.save(new UserDocument(user)).toDomain();
  }

  @Override
  public void deleteById(String cpf) {
    userRepository.deleteById(cpf);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll().stream()
        .map(UserDocument::toDomain).collect(Collectors.toList());
  }

  @Override
  public Optional<User> findById(String cpf) {
    return userRepository.findById(cpf).map(UserDocument::toDomain);
  }

}
