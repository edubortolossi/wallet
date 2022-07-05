package com.challenge.wallet.gateways;


import com.challenge.wallet.domains.User;
import java.util.List;
import java.util.Optional;

public interface UserDataGateway {

  User save(final User user);

  void deleteById(final String cpf);

  List<User> findAll();

  Optional<User> findById(final String cpf);
}
