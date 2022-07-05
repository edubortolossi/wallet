package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.User;
import com.challenge.wallet.gateways.UserDataGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAllUsers {

  private final UserDataGateway userDataGateway;

  public List<User> execute() {
    return userDataGateway.findAll();
  }
}
