package com.dsomorov.email.services;

import com.dsomorov.email.mappers.UserMapper;
import com.dsomorov.email.models.dtos.UserDto;
import com.dsomorov.email.models.entities.User;
import com.dsomorov.email.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService
{
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper     userMapper;
  
  public UserDto createUser(UserDto userDto)
  {
    User savedUser = userRepository.save(userMapper.mapFrom(userDto));
    return userMapper.mapTo(savedUser);
  }
  
  public Optional<UserDto> findUserById(Long id)
  {
    Optional<User> foundUser = userRepository.findById(id);
    return foundUser.map(userMapper::mapTo);
  }
  
  public boolean existsById(Long id)
  {
    return userRepository.existsById(id);
  }
  
}
