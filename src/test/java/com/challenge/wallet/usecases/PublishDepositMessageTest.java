package com.challenge.wallet.usecases;

import static org.mockito.ArgumentMatchers.any;

import com.challenge.wallet.domains.Account;
import com.challenge.wallet.gateways.outputs.kafka.BroadcastDepositProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PublishDepositMessageTest {

  @InjectMocks
  private PublishDepositMessage publishDepositMessage;

  @Mock
  private BroadcastDepositProducer broadcastDepositProducer;

  @Test
  public void successfulPublishDepositMessage() {
    var account = Account.builder()
        .accountNumber("000054788").cpf("1234567897").value(100L).build();

    publishDepositMessage.execute(account);
    Mockito.verify(broadcastDepositProducer, Mockito.times(1)).send(any());
  }
}
