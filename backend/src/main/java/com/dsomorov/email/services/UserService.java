package com.dsomorov.email.services;

import com.dsomorov.email.mappers.UserMapper;
import com.dsomorov.email.models.dtos.UserDto;
import com.dsomorov.email.models.entities.Address;
import com.dsomorov.email.models.entities.User;
import com.dsomorov.email.repositories.AddressRepository;
import com.dsomorov.email.repositories.UserRepository;
import com.dsomorov.email.validation.RuntimeValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService
{
  @Autowired
  private UserRepository    userRepository;
  @Autowired
  private UserMapper        userMapper;
  @Autowired
  private AddressRepository addressRepository;
  
  public UserDto createUser(@Valid UserDto userDto)
  {
    User savedUser = userRepository.save(userMapper.mapFrom(userDto));
    return userMapper.mapTo(savedUser);
  }
  
  public Optional<UserDto> findUserById(Long id)
  {
    Optional<User> foundUser = userRepository.findById(id);
    return foundUser.map(userMapper::mapTo);
  }
  
  public UserDto findUserByLogin(UserDto userDto)
  {
    Address foundAddress = addressRepository
      .findByUsernameAndServer(userDto.getUsername(), userDto.getServer())
      .orElseThrow(() -> new RuntimeValidationException("Invalid username or password"));
    
    User foundUser = userRepository
      .findByPasswordAndAddress(userDto.getPassword(), foundAddress)
      .orElseThrow(() -> new RuntimeValidationException("Invalid username or password"));
    
    return userMapper.mapTo(foundUser);
  }
  
  public boolean existsById(Long id)
  {
    return userRepository.existsById(id);
  }
  
}
