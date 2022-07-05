package com.challenge.wallet.gateways.inputs.http.external.resources.request;

import com.challenge.wallet.domains.User;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

  @NotNull
  private String cpf;

  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  private String phoneNumber;

  public User toDomain() {
    return User.builder()
        .cpf(cpf)
        .name(name)
        .email(email)
        .phoneNumber(phoneNumber)
        .build();
  }
}
