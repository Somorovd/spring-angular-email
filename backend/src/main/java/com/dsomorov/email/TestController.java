package com.dsomorov.email;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  
  @GetMapping(value = "/api/hello")
  @ResponseStatus(HttpStatus.OK)
  public StringResponse sayHello() {
    return new StringResponse("Hello from Spring!");
  }
}
