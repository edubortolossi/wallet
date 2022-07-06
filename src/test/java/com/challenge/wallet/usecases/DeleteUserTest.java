package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.anyString;

import com.challenge.wallet.gateways.UserDataGateway;
import com.challenge.wallet.usecases.DeleteUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUserTest {

  @InjectMocks
  private DeleteUser deleteUser;

  @Mock
  private UserDataGateway userDataGateway;

  @Test
  public void successfulDeleteUser() {
    deleteUser.execute("1234567897");
    Mockito.verify(userDataGateway, Mockito.times(1)).deleteById(anyString());
  }
}
