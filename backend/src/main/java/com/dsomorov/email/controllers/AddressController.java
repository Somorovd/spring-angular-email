package com.dsomorov.email.controllers;

import com.dsomorov.email.models.entities.Address;
import com.dsomorov.email.repositories.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping(path = "/addresses")
public class AddressController
{
  
  private final AddressRepository addressRepository;
  
  public AddressController(AddressRepository addressRepository)
  {
    this.addressRepository = addressRepository;
  }
  
  @PostMapping()
  public ResponseEntity<Address> createAddress(@RequestBody Address address)
  {
    Address savedAddress = this.addressRepository.save(address);
    return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
  }
  
  @GetMapping()
  public ResponseEntity<List<Address>> findAllAddresses()
  {
    List<Address> addresses = StreamSupport
      .stream(this.addressRepository.findAll().spliterator(), false)
      .toList();
    return new ResponseEntity<>(addresses, HttpStatus.OK);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Address> findAddressById(@PathVariable("id") Long id)
  {
    Optional<Address> foundAddress = this.addressRepository.findById(id);
    return foundAddress
      .map(address -> new ResponseEntity<>(address, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<Address> fullUpdateAddress
    (
      @PathVariable("id") Long id,
      @RequestBody Address address
    )
  {
    if (!this.addressRepository.existsById(id))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    Address updatedAddress = this.addressRepository
      .findById(id)
      .map(foundAddress -> {
        Optional.ofNullable(address.getServer()).ifPresent(foundAddress::setServer);
        Optional.ofNullable(address.getUsername()).ifPresent(foundAddress::setUsername);
        return this.addressRepository.save(foundAddress);
      })
      .orElseThrow(() -> new RuntimeException("Address does not exists"));
    return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
  }
}
