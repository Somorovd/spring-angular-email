package com.dsomorov.email.mappers;

import com.dsomorov.email.models.dtos.StatusDto;
import com.dsomorov.email.models.entities.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StatusMapper implements Mapper<Status, StatusDto>
{
  @Autowired
  private ModelMapper modelMapper;
  
  @Override
  public StatusDto mapTo(Status status)
  {
    return modelMapper.map(status, StatusDto.class);
  }
  
  @Override
  public Status mapFrom(StatusDto statusDto)
  {
    return modelMapper.map(statusDto, Status.class);
  }
}
