package com.dsomorov.email.services;

import com.dsomorov.email.enums.Location;
import com.dsomorov.email.mappers.AddressMapper;
import com.dsomorov.email.mappers.EmailMapper;
import com.dsomorov.email.mappers.RecipientMapper;
import com.dsomorov.email.mappers.StatusMapper;
import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.models.dtos.RecipientDto;
import com.dsomorov.email.models.dtos.StatusDto;
import com.dsomorov.email.models.entities.*;
import com.dsomorov.email.repositories.*;
import com.dsomorov.email.validation.RuntimeValidationException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.*;


@Service
public class EmailService
{
  
  @Autowired
  private EmailRepository     emailRepository;
  @Autowired
  private EmailMapper         emailMapper;
  @Autowired
  private AddressRepository   addressRepository;
  @Autowired
  private AddressMapper       addressMapper;
  @Autowired
  private RecipientRepository recipientRepository;
  @Autowired
  private RecipientMapper     recipientMapper;
  @Autowired
  private UserRepository      userRepository;
  @Autowired
  private ChainRepository     chainRepository;
  @Autowired
  private StatusRepository    statusRepository;
  @Autowired
  private StatusMapper        statusMapper;
  
  @Transactional
  public EmailDto createEmail(@Valid EmailDto emailDto)
  {
    Address foundSenderAddress = addressRepository.findByUsernameAndServer(
      emailDto.getSender().getUsername(),
      emailDto.getSender().getServer()
    ).orElseGet(() -> {
      Address address = Address
        .builder()
        .username(emailDto.getSender().getUsername())
        .server(emailDto.getSender().getServer())
        .build();
      return addressRepository.save(address);
    });
    
    if (emailDto.getChainId() == null)
    {
      Chain chain = chainRepository.save(new Chain());
      emailDto.setChainId(chain.getId());
    }
    else if (!chainRepository.existsById(emailDto.getChainId()))
    {
      throw new RuntimeValidationException("Chain Id does not exist");
    }
    
    Email email = emailMapper.mapFrom(emailDto);
    email.setSenderAddress(foundSenderAddress);
    email.setDate(Date.from(Clock.systemUTC().instant()));
    Email savedEmail = emailRepository.save(email);
    
    List<Recipient> savedRecipients = this._saveEmailRecipients(emailDto.getRecipients(), savedEmail);
    
    if (!savedEmail.getIsDraft())
    {
      this._createStatuses(savedRecipients, savedEmail);
    }
    
    savedEmail.setRecipients(savedRecipients);
    return emailMapper.mapTo(savedEmail);
  }
  
  public Optional<EmailDto> findEmailById(Long id)
  {
    Optional<Email> foundEmail = emailRepository.findById(id);
    return foundEmail.map(emailMapper::mapTo);
  }
  
  @Transactional
  public EmailDto updateEmail(Long id, @Valid EmailDto emailDto)
  {
    Email savedEmail = emailRepository
      .findById(id)
      .map(foundEmail -> {
        Optional.ofNullable(emailDto.getSubject()).ifPresent(foundEmail::setSubject);
        Optional.ofNullable(emailDto.getBody()).ifPresent(foundEmail::setBody);
        Optional.ofNullable(emailDto.getIsDraft()).ifPresent(foundEmail::setIsDraft);
        foundEmail.setDate(Date.from(Clock.systemUTC().instant()));
        return emailRepository.save(foundEmail);
      })
      .orElseThrow(() -> new RuntimeException("Email does not exist"));
    
    List<Recipient> savedRecipients = this._saveEmailRecipients(emailDto.getRecipients(), savedEmail);
    this._deleteMissingRecipients(savedRecipients, savedEmail);
    
    if (!savedEmail.getIsDraft())
    {
      this._createStatuses(savedRecipients, savedEmail);
    }
    
    savedEmail.setRecipients(savedRecipients);
    return emailMapper.mapTo(savedEmail);
  }
  
  public boolean existsById(Long id)
  {
    return emailRepository.existsById(id);
  }
  
  
  @Transactional
  public List<EmailDto> findDraftEmailsByUserId(Long userId)
  {
    return emailRepository
      .findDraftEmailsByUserId(userId)
      .stream()
      .map(emailMapper::mapTo)
      .map(EmailDto::asSummary)
      .toList();
  }
  
  @Transactional
  public List<EmailDto> findSentEmailsByUserId(Long userId)
  {
    return emailRepository
      .findSentEmailsByUserId(userId)
      .stream()
      .map(emailMapper::mapTo)
      .map(EmailDto::asSummary)
      .toList();
  }
  
  @Transactional
  public List<StatusDto> findUserInbox(Long userId)
  {
    return statusRepository
      .findUserInbox(userId)
      .stream()
      .map(statusMapper::mapTo)
      .toList();
  }
  
  private List<Recipient> _saveEmailRecipients(List<RecipientDto> recipientDtos, Email email)
  {
    return recipientDtos
      .stream()
      .map(recipientDto ->
           {
             if (recipientDto.getId() != null)
             {
               return recipientRepository
                 .findById(recipientDto.getId())
                 .orElseThrow(() -> new RuntimeException("Invalid Recipient Id: " + recipientDto.getId().toString()));
             }
             else
             {
               Address foundAddress = addressRepository.findByUsernameAndServer(
                 recipientDto.getUsername(),
                 recipientDto.getServer()
               ).orElseGet(() -> {
                 Address address = Address
                   .builder()
                   .username(recipientDto.getUsername())
                   .server(recipientDto.getServer())
                   .build();
                 return addressRepository.save(address);
               });
               
               return recipientRepository
                 .findByEmailAndAddress(email, foundAddress)
                 .orElseGet(() -> {
                   Recipient recipient = recipientMapper.mapFrom(recipientDto);
                   recipient.setAddress(foundAddress);
                   recipient.setEmail(email);
                   return recipientRepository.save(recipient);
                 });
             }
           })
      .toList();
  }
  
  private void _deleteMissingRecipients(List<Recipient> savedRecipients, Email email)
  {
    Set<Long> foundRecipientIds = new HashSet<>(
      recipientRepository
        .findByEmailId(email.getId())
        .stream()
        .map(Recipient::getId)
        .toList()
    );
    
    savedRecipients.forEach(recipientDto -> foundRecipientIds.remove(recipientDto.getId()));
    
    recipientRepository.deleteAllById(foundRecipientIds.stream().toList());
  }
  
  private void _createStatuses(List<Recipient> savedRecipients, Email email)
  {
    savedRecipients
      .forEach(recipient -> {
        if (!Objects.equals(recipient.getAddress().getServer(), "dsomorov.xyz"))
        {
          return;
        }
        Status foundStatus = statusRepository
          .findByAddressAndChainAndLocation(
            recipient.getAddress(),
            email.getChain(),
            Location.INBOX
          )
          .orElseGet(() -> Status
            .builder()
            .address(recipient.getAddress())
            .chain(email.getChain())
            .isStarred(false)
            .location(Location.INBOX)
            .build()
          );
        foundStatus.setRead(false);
        foundStatus.setEmail(email);
        statusRepository.save(foundStatus);
      });
    
  }
}

