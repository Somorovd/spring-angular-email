package com.dsomorov.email.services;

import com.dsomorov.email.mappers.AddressMapper;
import com.dsomorov.email.models.dtos.AddressDto;
import com.dsomorov.email.models.entities.Address;
import com.dsomorov.email.repositories.AddressRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;


@Service
public class AddressService
{
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private AddressMapper     addressMapper;
  
  public AddressDto createAddress(@Valid AddressDto addressDto)
  {
    Address savedAddress = addressRepository.save(addressMapper.mapFrom(addressDto));
    return addressMapper.mapTo(savedAddress);
  }
  
  public List<AddressDto> findAllAddresses()
  {
    return StreamSupport
      .stream(addressRepository.findAll().spliterator(), false)
      .map(addressMapper::mapTo)
      .toList();
  }
  
  public Optional<AddressDto> findAddressById(Long id)
  {
    Optional<Address> foundAddress = addressRepository.findById(id);
    return foundAddress.map(addressMapper::mapTo);
  }
  
  public AddressDto updateAddress(Long id, @Valid AddressDto updateDto)
  {
    Address savedAddress = addressRepository
      .findById(id)
      .map(foundAddress -> {
        Optional.ofNullable(updateDto.getServer()).ifPresent(foundAddress::setServer);
        Optional.ofNullable(updateDto.getUsername()).ifPresent(foundAddress::setUsername);
        return addressRepository.save(foundAddress);
      })
      .orElseThrow(() -> new RuntimeException("Address does not exist"));
    return addressMapper.mapTo(savedAddress);
  }
  
  public boolean existsById(Long id)
  {
    return addressRepository.existsById(id);
  }
}
