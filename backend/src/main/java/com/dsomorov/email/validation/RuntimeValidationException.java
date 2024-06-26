package com.dsomorov.email.validation;

public class RuntimeValidationException extends RuntimeException
{
  public RuntimeValidationException(String message)
  {
    super(message);
  }
}