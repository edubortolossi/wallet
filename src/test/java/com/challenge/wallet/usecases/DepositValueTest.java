package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.exceptions.NotFoundException;
import com.challenge.wallet.gateways.AccountDataGateway;
import com.challenge.wallet.utils.JsonUtils;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DepositValueTest {

  @InjectMocks
  private DepositValue depositValue;

  @Mock
  private AccountDataGateway accountDataGateway;

  @Mock
  private PublishHistoricMessage publishHistoricMessage;

  @Mock
  private JsonUtils jsonUtils;

  @Test
  public void successfulDepositValue() {
    Account account = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(100L).build();
    Account accountToDeposit = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(0L).build();
    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.of(accountToDeposit));
    depositValue.execute(account);

    Mockito.verify(publishHistoricMessage, Mockito.times(1)).execute(any());
    Mockito.verify(accountDataGateway, Mockito.times(1)).save(any());
  }

  @Test(expected = NotFoundException.class)
  public void errorDepositValue() {
    Account account = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(100L).build();
    Account accountToDeposit = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(0L).build();
    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.empty());
    depositValue.execute(account);
  }
}
