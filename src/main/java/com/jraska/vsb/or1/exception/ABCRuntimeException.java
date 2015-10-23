package com.jraska.vsb.or1.exception;

public class ABCRuntimeException extends RuntimeException {
  //region Constructors

  public ABCRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public ABCRuntimeException(Throwable cause) {
    super(cause);
  }

  //endregion
}
