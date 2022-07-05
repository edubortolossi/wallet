package com.challenge.wallet.usecases;

import com.challenge.wallet.gateways.UserDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUser {

  private final UserDataGateway userDataGateway;

  public void execute(final String user) {
    userDataGateway.deleteById(user);
  }
}
