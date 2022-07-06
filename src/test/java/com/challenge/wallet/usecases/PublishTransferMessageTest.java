package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;

import com.challenge.wallet.domains.Payment;
import com.challenge.wallet.domains.TransferValue;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastTransferProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PublishTransferMessageTest {

  @InjectMocks
  private PublishTransferMessage publishTransferMessage;

  @Mock
  private BroadcastTransferProducer broadcastTransferProducer;

  @Test
  public void successfulPublishPaymentMessage() {
    TransferValue transferValue = TransferValue.builder()
        .cpfFrom("1234567897")
        .cpfTo("1234567887")
        .accountTo("000000555112345")
        .accountFrom("000000545672345")
        .value(100L)
        .build();

    publishTransferMessage.execute(transferValue);
    Mockito.verify(broadcastTransferProducer, Mockito.times(1)).send(any());
  }
}
