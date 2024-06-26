package com.dsomorov.email.advice;

import com.dsomorov.email.validation.RuntimeValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ValidationExceptionHandler
{
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex)
  {
    Map<String, String> errorMap = new HashMap<>();
    ex.getBindingResult()
      .getFieldErrors()
      .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
    ex.getBindingResult()
      .getGlobalErrors()
      .forEach(error -> errorMap.put(error.getObjectName(), error.getDefaultMessage()));
    return errorMap;
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RuntimeValidationException.class)
  public Map<String, String> handleRuntimeValidationException(RuntimeValidationException ex)
  {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("error", ex.getMessage());
    return errorMap;
  }
}
