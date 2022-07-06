package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.Payment;
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
public class BillsPaymentTest {

  @InjectMocks
  private BillsPayment billsPayment;

  @Mock
  private AccountDataGateway accountDataGateway;

  @Mock
  private PublishHistoricMessage publishHistoricMessage;

  @Mock
  private JsonUtils jsonUtils;

  @Test
  public void successfulBillPayment() {
    var payment = Payment.builder()
        .cpf("123456789")
        .description("Conta")
        .accountNumber("0001255485")
        .barCode("000000088884454848")
        .value(100L)
        .build();
    Account account = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(200L).build();
    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.of(account));

    billsPayment.execute(payment);
    Mockito.verify(accountDataGateway, Mockito.times(1)).findById(anyString(), anyString());
    Mockito.verify(accountDataGateway, Mockito.times(1)).save(any());
    Mockito.verify(publishHistoricMessage, Mockito.times(1)).execute(any());
  }

  @Test(expected = NotFoundException.class)
  public void errorAccountNotFoundBillPayment() {
    var payment = Payment.builder()
        .cpf("123456789")
        .description("Conta")
        .accountNumber("0001255485")
        .barCode("000000088884454848")
        .value(100L)
        .build();
    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.empty());

    billsPayment.execute(payment);
  }

  @Test(expected = BusinessException.class)
  public void errorWithoutBalanceBillPayment() {
    var payment = Payment.builder()
        .cpf("123456789")
        .description("Conta")
        .accountNumber("0001255485")
        .barCode("000000088884454848")
        .value(100L)
        .build();
    Account account = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(50L).build();
    Mockito.when(accountDataGateway.findById(anyString(), anyString())).thenReturn(Optional.of(account));

    billsPayment.execute(payment);
  }

}
