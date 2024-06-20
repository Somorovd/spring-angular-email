package com.dsomorov.email.mappers;

import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.models.entities.Email;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EmailMapper implements Mapper<Email, EmailDto>
{
  @Autowired
  private ModelMapper modelMapper;
  
  @Override
  public EmailDto mapTo(Email email)
  {
    return modelMapper.map(email, EmailDto.class);
  }
  
  @Override
  public Email mapFrom(EmailDto emailDto)
  {
    return modelMapper.map(emailDto, Email.class);
  }
}
