package com.dsomorov.email.controllers;

import com.dsomorov.email.models.dtos.UserDto;
import com.dsomorov.email.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(path = "/api/users")
public class UserController
{
  @Autowired
  private UserService userService;
  
  @PostMapping()
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
  {
    UserDto savedUserDto = userService.createUser(userDto);
    return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> findUserById(@PathVariable("id") Long id)
  {
    Optional<UserDto> foundUserDto = userService.findUserById(id);
    return foundUserDto
      .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
