package com.challenge.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class WalletApplication {

  public static void main(final String[] args) {
    SpringApplication.run(WalletApplication.class, args);
  }
}