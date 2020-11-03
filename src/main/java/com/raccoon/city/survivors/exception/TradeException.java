package com.raccoon.city.survivors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TradeException extends RuntimeException {

  public TradeException(String message) {
    super(message);
  }

}