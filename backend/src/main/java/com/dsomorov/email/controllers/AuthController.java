package com.dsomorov.email.controllers;

import com.dsomorov.email.models.dtos.UserDto;
import com.dsomorov.email.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/auth")
public class AuthController
{
  @Autowired
  private UserService userService;
  
  @PostMapping("/login")
  public ResponseEntity<UserDto> loginUser(@Valid @RequestBody UserDto userDto)
  {
    return new ResponseEntity<>(userService.findUserByLogin(userDto), HttpStatus.OK);
  }
}
