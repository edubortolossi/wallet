package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.gateways.AccountDataGateway;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FindAllAccountsTest {

  @InjectMocks
  private FindAllAccounts findAllAccounts;

  @Mock
  private AccountDataGateway accountDataGateway;

  @Test
  public void successfulFindAllAccounts() {
    Account account1 = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(100L).build();
    Account account2 = Account.builder()
        .accountNumber("0001255485")
        .cpf("123456789")
        .value(0L).build();

    Mockito.when(accountDataGateway.findAll()).thenReturn(List.of(account1, account2));

    List<Account> accounts = findAllAccounts.execute();
    Assert.assertEquals(accounts.size(), 2L);
    Assert.assertEquals(accounts.get(0), account1);
    Assert.assertEquals(accounts.get(1), account2);
  }

  @Test
  public void successfulFindAllZeroAccounts() {
    Mockito.when(accountDataGateway.findAll()).thenReturn(List.of());

    List<Account> accounts = findAllAccounts.execute();
    Assert.assertEquals(accounts.size(), 0L);
  }

}
