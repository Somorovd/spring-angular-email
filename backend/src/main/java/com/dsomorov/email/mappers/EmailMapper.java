package com.dsomorov.email.mappers;

import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.models.entities.Email;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EmailMapper implements Mapper<Email, EmailDto>
{
  @Autowired
  private ModelMapper modelMapper;
  
  @PostConstruct
  private void setupMapper()
  {
    TypeMap<Email, EmailDto> emailToEmailDtoTypeMap = modelMapper.createTypeMap(Email.class, EmailDto.class);
    emailToEmailDtoTypeMap.addMapping(src -> src.getChain().getSubject(), EmailDto::setSubject);
  }
  
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
