package com.dsomorov.email.mappers;

import com.dsomorov.email.models.dtos.RecipientDto;
import com.dsomorov.email.models.entities.Recipient;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RecipientMapper implements Mapper<Recipient, RecipientDto>
{
  @Autowired
  private ModelMapper modelMapper;
  
  @PostConstruct
  private void setupMapper()
  {
    TypeMap<Recipient, RecipientDto> recipientToRecipientDtoTypeMap = modelMapper.createTypeMap(
      Recipient.class,
      RecipientDto.class
    );
    recipientToRecipientDtoTypeMap.addMapping(src -> src.getAddress().getUsername(), RecipientDto::setUsername);
    recipientToRecipientDtoTypeMap.addMapping(src -> src.getAddress().getServer(), RecipientDto::setServer);
    
    TypeMap<RecipientDto, Recipient> recipientDtoToRecipientTypeMap = modelMapper.createTypeMap(
      RecipientDto.class,
      Recipient.class
    );
    recipientDtoToRecipientTypeMap.addMapping(
      RecipientDto::getUsername,
      (dest, v) -> dest.getAddress().setUsername((String) v)
    );
    recipientDtoToRecipientTypeMap.addMapping(
      RecipientDto::getServer,
      (dest, v) -> dest.getAddress().setServer((String) v)
    );
    
  }
  
  @Override
  public RecipientDto mapTo(Recipient recipient)
  {
    return modelMapper.map(recipient, RecipientDto.class);
  }
  
  @Override
  public Recipient mapFrom(RecipientDto recipientDto)
  {
    return modelMapper.map(recipientDto, Recipient.class);
  }
}
