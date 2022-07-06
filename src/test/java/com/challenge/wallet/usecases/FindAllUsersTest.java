package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.User;
import com.challenge.wallet.gateways.UserDataGateway;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FindAllUsersTest {

  @InjectMocks
  private FindAllUsers findAllUsers;

  @Mock
  private UserDataGateway userDataGateway;

  @Test
  public void successfulFindAllUsers() {
    var user1 = User.builder()
        .cpf("1234567897")
        .phoneNumber("1137152486")
        .email("test@test.com")
        .name("Teste").build();

    var user2 = User.builder()
        .cpf("4534567897")
        .phoneNumber("4537152486")
        .email("testqw@test.com")
        .name("Testeas").build();

    Mockito.when(userDataGateway.findAll()).thenReturn(List.of(user1, user2));
    List<User> users = findAllUsers.execute();
    Assert.assertEquals(users.size(), 2L);
    Assert.assertEquals(users.get(0), user1);
    Assert.assertEquals(users.get(1), user2);
  }


  @Test
  public void successfulFindAllZeroUsers() {
    Mockito.when(userDataGateway.findAll()).thenReturn(List.of());
    List<User> users = findAllUsers.execute();
    Assert.assertEquals(users.size(), 0L);
  }
}
