package com.challenge.wallet.gateways.outputs.mongodb.documents;

import com.challenge.wallet.domains.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("user")
public class UserDocument {
  @Id
  private String cpf;
  private String name;
  private String email;
  private String phoneNumber;

  public UserDocument(final User user) {
    this.cpf = user.getCpf();
    this.name = user.getName();
    this.email = user.getEmail();
    this.phoneNumber = user.getPhoneNumber();
  }

  public User toDomain() {
    return User.builder()
        .cpf(cpf)
        .name(name)
        .email(email)
        .phoneNumber(phoneNumber)
        .build();
  }
}
