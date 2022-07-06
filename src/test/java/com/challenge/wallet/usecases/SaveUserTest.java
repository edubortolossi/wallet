package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.challenge.wallet.domains.User;
import com.challenge.wallet.exceptions.ConflictException;
import com.challenge.wallet.gateways.UserDataGateway;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaveUserTest {

  @InjectMocks
  private SaveUser saveUser;

  @Mock
  private UserDataGateway userDataGateway;

  @Test
  public void successfulSaveUser() {
    var user = User.builder().cpf("1234567897").email("test@test.com").name("Teste").phoneNumber("1125487895").build();
    Mockito.when(userDataGateway.findById(anyString())).thenReturn(Optional.empty());
    Mockito.when(userDataGateway.save(any())).thenReturn(user);
    var userReturn = saveUser.execute(user);
    Assert.assertEquals(user.getEmail(), userReturn.getEmail());
    Assert.assertEquals(user.getName(), userReturn.getName());
    Assert.assertEquals(user.getPhoneNumber(), userReturn.getPhoneNumber());
  }

  @Test(expected = ConflictException.class)
  public void errorSaveUser() {
    var user = User.builder().cpf("1234567897").email("test@test.com").name("Teste").phoneNumber("1125487895").build();
    Mockito.when(userDataGateway.findById(anyString())).thenReturn(Optional.of(user));
    var userReturn = saveUser.execute(user);
    Assert.assertEquals(user.getEmail(), userReturn.getEmail());
    Assert.assertEquals(user.getName(), userReturn.getName());
    Assert.assertEquals(user.getPhoneNumber(), userReturn.getPhoneNumber());
  }

}
