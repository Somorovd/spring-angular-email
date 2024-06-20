package com.dsomorov.email.controllers;

import com.dsomorov.email.models.dtos.AddressDto;
import com.dsomorov.email.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/addresses")
public class AddressController
{
  
  @Autowired
  private AddressService addressService;
  
  @PostMapping()
  public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto)
  {
    AddressDto savedAddressDto = addressService.createAddress(addressDto);
    return new ResponseEntity<>(savedAddressDto, HttpStatus.CREATED);
  }
  
  @GetMapping()
  public ResponseEntity<List<AddressDto>> findAllAddresses()
  {
    List<AddressDto> foundAddressDtos = addressService.findAllAddresses();
    return new ResponseEntity<>(foundAddressDtos, HttpStatus.OK);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<AddressDto> findAddressById(@PathVariable("id") Long id)
  {
    Optional<AddressDto> foundAddressDto = addressService.findAddressById(id);
    return foundAddressDto
      .map(addressDto -> new ResponseEntity<>(addressDto, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<AddressDto> updateAddress
    (
      @PathVariable("id") Long id,
      @RequestBody AddressDto addressDto
    )
  {
    if (!addressService.existsById(id))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    AddressDto updatedAddressDto = addressService.updateAddress(id, addressDto);
    return new ResponseEntity<>(updatedAddressDto, HttpStatus.ACCEPTED);
  }
}
