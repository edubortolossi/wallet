package com.challenge.wallet.gateways.inputs.http.external.resources.responses;

import com.challenge.wallet.domains.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private String cpf;
  private String name;
  private String email;
  private String phoneNumber;

  public UserResponse(final User user) {
    this.cpf = user.getCpf();
    this.name = user.getName();
    this.email = user.getEmail();
    this.phoneNumber = user.getPhoneNumber();
  }
}
