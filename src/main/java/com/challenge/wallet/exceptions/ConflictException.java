package com.challenge.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

  private static final long serialVersionUID = -7188798134377196967L;

  public ConflictException() {
  }

  public ConflictException(final String message) {
    super(message);
  }
}
