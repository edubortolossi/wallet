package com.challenge.wallet.usecases;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.HistoricDataGateway;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class FindHistoricByCpfAndAccountTest {

  @InjectMocks
  private FindHistoricByCpfAndAccount findHistoricByCpfAndAccount;

  @Mock
  private HistoricDataGateway historicDataGateway;

  @Test
  public void successfulFindHistoricByCpfAndAccount() {
    Historic historic = Historic.builder()
        .cpf("1234567897")
        .accountNumber("000005789")
        .operation("DEPOSIT")
        .payload("{'cpf\":\"36768246979\",\"accountNumber\":\"000121214574\",\"value\":50}")
        .build();
    Mockito.when(historicDataGateway.findByCpfAndAccountNumber(any(), any())).thenReturn(List.of(historic));
    List<Historic> historics = findHistoricByCpfAndAccount.execute("1234567897", "000005789");
    Assert.assertEquals(historics.size(), 1L);
    Assert.assertEquals(historics.get(0), historic);
  }

  @Test
  public void successfulFindHistoricByCpfAndAccountZeroResults() {
    Mockito.when(historicDataGateway.findByCpfAndAccountNumber(any(), any())).thenReturn(List.of());
    List<Historic> historics = findHistoricByCpfAndAccount.execute("1234567897", "000005789");
    Assert.assertEquals(historics.size(), 0L);
  }


}
