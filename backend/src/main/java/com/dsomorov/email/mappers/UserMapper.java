package com.dsomorov.email.mappers;

import com.dsomorov.email.models.dtos.UserDto;
import com.dsomorov.email.models.entities.User;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserMapper implements Mapper<User, UserDto>
{
  @Autowired
  private ModelMapper modelMapper;
  
  @PostConstruct
  private void setupMapper()
  {
    TypeMap<User, UserDto> userToUserDtoTypeMap = modelMapper.createTypeMap(User.class, UserDto.class);
    userToUserDtoTypeMap.addMapping(src -> src.getAddress().getUsername(), UserDto::setUsername);
    userToUserDtoTypeMap.addMapping(src -> src.getAddress().getServer(), UserDto::setServer);
    
    TypeMap<UserDto, User> userDtoToUserTypeMap = modelMapper.createTypeMap(UserDto.class, User.class);
    userDtoToUserTypeMap.addMapping(UserDto::getUsername, (dest, v) -> dest.getAddress().setUsername((String) v));
    userDtoToUserTypeMap.addMapping(UserDto::getServer, (dest, v) -> dest.getAddress().setServer((String) v));
  }
  
  @Override
  public UserDto mapTo(User user)
  {
    return modelMapper.map(user, UserDto.class);
  }
  
  @Override
  public User mapFrom(UserDto userDto)
  {
    return modelMapper.map(userDto, User.class);
  }
}