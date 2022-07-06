package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;

import com.challenge.wallet.domains.Historic;
import com.challenge.wallet.domains.Payment;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastHistoricProducer;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastPaymentProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PublishPaymentMessageTest {

  @InjectMocks
  private PublishPaymentMessage publishPaymentMessage;

  @Mock
  private BroadcastPaymentProducer broadcastPaymentProducer;

  @Test
  public void successfulPublishPaymentMessage() {
    Payment payment = Payment.builder().accountNumber("0000052784")
            .cpf("1234567897").value(100L).build();

    publishPaymentMessage.execute(payment);
    Mockito.verify(broadcastPaymentProducer, Mockito.times(1)).send(any());
  }
}
