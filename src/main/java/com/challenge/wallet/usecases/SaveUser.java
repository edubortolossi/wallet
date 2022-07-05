package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.User;
import com.challenge.wallet.exceptions.BusinessException;
import com.challenge.wallet.exceptions.ConflictException;
import com.challenge.wallet.gateways.UserDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveUser {

  private final UserDataGateway userDataGateway;

  public User execute(final User user) {
    validateUserExists(user);
    return userDataGateway.save(user);
  }

  private void validateUserExists(User user) {
    if(userDataGateway.findById(user.getCpf()).isPresent()) {
      throw new ConflictException("User already exist");
    }
  }

}
