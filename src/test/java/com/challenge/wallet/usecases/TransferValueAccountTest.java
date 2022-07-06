package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.TransferValue;
import com.challenge.wallet.domains.User;
import com.challenge.wallet.exceptions.BusinessException;
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
public class TransferValueAccountTest {

  @InjectMocks
  private TransferValueAccount transferValueAccount;

  @Mock
  private AccountDataGateway accountDataGateway;

  @Mock
  private PublishHistoricMessage publishHistoricMessage;

  @Mock
  private JsonUtils jsonUtils;

  @Test
  public void successfulTransferValueAccount() {
    var transfer = TransferValue.builder()
        .accountFrom("00000005477474")
        .cpfFrom("1234567897")
        .accountTo("00000005478796")
        .cpfTo("9876543211")
        .value(10L).build();

    var accountFrom = Account.builder()
        .accountNumber("00000005477474")
        .cpf("1234567897")
        .value(100L).build();
    var accountTo = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(0L).build();
    Mockito.when(accountDataGateway.findById("1234567897", "00000005477474")).thenReturn(Optional.of(accountFrom));
    Mockito.when(accountDataGateway.findById("9876543211", "00000005478796")).thenReturn(Optional.of(accountTo));

    transferValueAccount.execute(transfer);
    Mockito.verify(accountDataGateway, Mockito.times(2)).save(any());
    Mockito.verify(publishHistoricMessage, Mockito.times(1)).execute(any());
  }

  @Test(expected = NotFoundException.class)
  public void errorTransferValueAccountToNotFound() {
    var transfer = TransferValue.builder()
        .accountFrom("00000005477474")
        .cpfFrom("1234567897")
        .accountTo("00000005478796")
        .cpfTo("9876543211")
        .value(10L).build();

    var accountFrom = Account.builder()
        .accountNumber("00000005477474")
        .cpf("1234567897")
        .value(100L).build();
    Mockito.when(accountDataGateway.findById("1234567897", "00000005477474")).thenReturn(Optional.of(accountFrom));
    Mockito.when(accountDataGateway.findById("9876543211", "00000005478796")).thenReturn(Optional.empty());

    transferValueAccount.execute(transfer);
  }

  @Test(expected = NotFoundException.class)
  public void errorTransferValueAccountFromNotFound() {
    var transfer = TransferValue.builder()
        .accountFrom("00000005477474")
        .cpfFrom("1234567897")
        .accountTo("00000005478796")
        .cpfTo("9876543211")
        .value(10L).build();

    var accountTo = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(0L).build();
    Mockito.when(accountDataGateway.findById("1234567897", "00000005477474")).thenReturn(Optional.empty());

    transferValueAccount.execute(transfer);
  }

  @Test(expected = BusinessException.class)
  public void errorTransferValueAccountZeroValue() {
    var transfer = TransferValue.builder()
        .accountFrom("00000005477474")
        .cpfFrom("1234567897")
        .accountTo("00000005478796")
        .cpfTo("9876543211")
        .value(10L).build();

    var accountFrom = Account.builder()
        .accountNumber("00000005477474")
        .cpf("1234567897")
        .value(0L).build();
    var accountTo = Account.builder()
        .accountNumber("00000005478796")
        .cpf("9876543211")
        .value(10L).build();
    Mockito.when(accountDataGateway.findById("1234567897", "00000005477474")).thenReturn(Optional.of(accountFrom));
    Mockito.when(accountDataGateway.findById("9876543211", "00000005478796")).thenReturn(Optional.of(accountTo));

    transferValueAccount.execute(transfer);
  }
}
