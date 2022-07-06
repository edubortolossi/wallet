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

@RunWith(MockitoJUnitRunner.class)
public class FindAllHistoricTest {

  @InjectMocks
  private FindAllHistoric findAllHistoric;

  @Mock
  private HistoricDataGateway historicDataGateway;

  @Test
  public void successfulFindAllHistoric() {
    Historic historic1 = Historic.builder()
        .cpf("1234567897")
        .accountNumber("000005474")
        .operation("DEPOSIT")
        .payload("{'cpf\":\"36768246979\",\"accountNumber\":\"000121214574\",\"value\":50}")
        .build();

    Historic historic2 = Historic.builder()
        .cpf("1234567897")
        .accountNumber("000005474")
        .operation("DEPOSIT")
        .payload("{'cpf\":\"36768246979\",\"accountNumber\":\"000121214574\",\"value\":60}")
        .build();
    Mockito.when(historicDataGateway.findAll()).thenReturn(List.of(historic1, historic2));
    List<Historic> historics = findAllHistoric.execute();
    Assert.assertEquals(historics.size(), 2L);
    Assert.assertEquals(historics.get(0), historic1);
    Assert.assertEquals(historics.get(1), historic2);
  }

  @Test
  public void successfulFindAllZeroHistoric() {
    Mockito.when(historicDataGateway.findAll()).thenReturn(List.of());
    List<Historic> historics = findAllHistoric.execute();
    Assert.assertEquals(historics.size(), 0L);
  }
}
