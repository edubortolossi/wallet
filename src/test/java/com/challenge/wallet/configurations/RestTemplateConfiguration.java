package com.challenge.wallet.configurations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("container-test")
public class RestTemplateConfiguration {

  @Bean
  @Primary
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder().errorHandler(new DefaultResponseErrorHandler() {
      @Override
      public void handleError(ClientHttpResponse response) {
        //do nothing
      }
    }).build();
  }
}