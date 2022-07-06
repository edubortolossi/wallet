package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.*;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.exceptions.BusinessException;
import com.challenge.wallet.exceptions.NotFoundException;
import com.challenge.wallet.gateways.AccountDataGateway;
import com.challenge.wallet.utils.JsonUtils;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WithdrawValueTest {

  @InjectMocks
  private WithdrawValue withdrawValue;

  @Mock
  private AccountDataGateway accountDataGateway;

  @Mock
  private PublishHistoricMessage publishHistoricMessage;

  @Mock
  private JsonUtils jsonUtils;

  @Test
  public void successfulWithdrawValue(){
    var accountWithdraw = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(10L).build();

    var account = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(100L).build();

    var accountExpected = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(90L).build();

    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.of(account));
    Mockito.when(accountDataGateway.save(any())).thenReturn(accountExpected);

    var accountReturn = withdrawValue.execute(accountWithdraw);
    Assert.assertEquals(90L, accountReturn.getValue());
    Assert.assertEquals(accountWithdraw.getCpf(), accountReturn.getCpf());
    Assert.assertEquals(accountWithdraw.getAccountNumber(), accountReturn.getAccountNumber());
  }

  @Test(expected = NotFoundException.class)
  public void errorWithdrawValueAccountNotFound(){
    var accountWithdraw = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(10L).build();

    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.empty());

    withdrawValue.execute(accountWithdraw);
  }

  @Test(expected = BusinessException.class)
  public void errorWithdrawValueWithoutBalance(){
    var accountWithdraw = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(10L).build();

    var account = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(0L).build();

    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.of(account));

    withdrawValue.execute(accountWithdraw);
  }

}
