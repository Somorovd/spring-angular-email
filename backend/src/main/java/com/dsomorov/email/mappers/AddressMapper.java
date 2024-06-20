package com.dsomorov.email.mappers;

import com.dsomorov.email.models.dtos.AddressDto;
import com.dsomorov.email.models.entities.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddressMapper implements Mapper<Address, AddressDto>
{
  @Autowired
  private ModelMapper modelMapper;
  
  @Override
  public AddressDto mapTo(Address address)
  {
    return modelMapper.map(address, AddressDto.class);
  }
  
  @Override
  public Address mapFrom(AddressDto addressDto)
  {
    return modelMapper.map(addressDto, Address.class);
  }
}
