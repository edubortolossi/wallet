package com.challenge.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = -7188798134377196962L;

  public BusinessException() {
  }

  public BusinessException(final String message) {
    super(message);
  }
}
