package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.User;
import com.challenge.wallet.exceptions.ConflictException;
import com.challenge.wallet.exceptions.NotFoundException;
import com.challenge.wallet.gateways.AccountDataGateway;
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
public class SaveAccountTest {

  @InjectMocks
  private SaveAccount saveAccount;

  @Mock
  private UserDataGateway userDataGateway;

  @Mock
  private AccountDataGateway accountDataGateway;

  @Test
  public void successfulSaveAccount() {
    var account = Account.builder()
        .accountNumber("00000005477474")
        .cpf("1234567897")
        .value(0L).build();
    var user = User.builder().cpf("1234567897").email("test@test.com").name("Teste").phoneNumber("1125487895").build();
    Mockito.when(userDataGateway.findById(anyString())).thenReturn(Optional.of(user));
    Mockito.when(accountDataGateway.save(any())).thenReturn(account);
    Account accountReturn = saveAccount.execute(account);
    Assert.assertEquals(account.getAccountNumber(), accountReturn.getAccountNumber());
    Assert.assertEquals(account.getCpf(), accountReturn.getCpf());
    Assert.assertEquals(account.getValue(), accountReturn.getValue());
  }

  @Test(expected = ConflictException.class)
  public void errorSaveAccountAlreadyExists() {
    var account = Account.builder()
        .accountNumber("00000005477474")
        .cpf("1234567897")
        .value(0L).build();
    var user = User.builder().cpf("1234567897").email("test@test.com").name("Teste").phoneNumber("1125487895").build();
    Mockito.when(userDataGateway.findById(anyString())).thenReturn(Optional.of(user));
    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.of(account));
    Account accountReturn = saveAccount.execute(account);
    Assert.assertEquals(account.getAccountNumber(), accountReturn.getAccountNumber());
    Assert.assertEquals(account.getCpf(), accountReturn.getCpf());
    Assert.assertEquals(account.getValue(), accountReturn.getValue());
  }

  @Test(expected = NotFoundException.class)
  public void errorSaveAccountUserNotFound() {
    var account = Account.builder()
        .accountNumber("00000005477474")
        .cpf("1234567897")
        .value(0L).build();
    var user = User.builder().cpf("1234567897").email("test@test.com").name("Teste").phoneNumber("1125487895").build();
    Mockito.when(userDataGateway.findById(anyString())).thenReturn(Optional.empty());
    Account accountReturn = saveAccount.execute(account);
    Assert.assertEquals(account.getAccountNumber(), accountReturn.getAccountNumber());
    Assert.assertEquals(account.getCpf(), accountReturn.getCpf());
    Assert.assertEquals(account.getValue(), accountReturn.getValue());
  }

}
