package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.HistoricDataGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessHistoricTest {

  @InjectMocks
  private ProcessHistoric processHistoric;

  @Mock
  private HistoricDataGateway historicDataGateway;

  @Test
  public void successfulProcessHistoric() {
    Historic historic = Historic.builder()
        .cpf("1234567897")
        .accountNumber("000005474")
        .operation("DEPOSIT")
        .payload("{'cpf\":\"36768246979\",\"accountNumber\":\"000121214574\",\"value\":50}")
        .build();

    processHistoric.execute(historic);
    Mockito.verify(historicDataGateway, Mockito.times(1)).save(any(), any(), any(), any());
  }
}
