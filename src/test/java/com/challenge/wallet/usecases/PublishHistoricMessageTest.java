package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;

import com.challenge.wallet.configurations.kafka.TopicProperties;
import com.challenge.wallet.domains.Account;
import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastHistoricProducer;
import com.challenge.wallet.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

@RunWith(MockitoJUnitRunner.class)
public class PublishHistoricMessageTest {

  @InjectMocks
  private PublishHistoricMessage publishHistoricMessage;

  @Mock
  private BroadcastHistoricProducer broadcastHistoricProducer;

  @Test
  public void successfulPublishDepositMessage() {
    Historic historic = Historic.builder()
        .cpf("1234567897")
        .accountNumber("000005789")
        .operation("DEPOSIT")
        .payload("{'cpf\":\"36768246979\",\"accountNumber\":\"000121214574\",\"value\":50}")
        .build();

    publishHistoricMessage.execute(historic);
    Mockito.verify(broadcastHistoricProducer, Mockito.times(1)).send(any());
  }
}
