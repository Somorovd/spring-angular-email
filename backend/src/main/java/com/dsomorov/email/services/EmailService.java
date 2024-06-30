package com.dsomorov.email.services;

import com.dsomorov.email.enums.Location;
import com.dsomorov.email.mappers.AddressMapper;
import com.dsomorov.email.mappers.EmailMapper;
import com.dsomorov.email.mappers.RecipientMapper;
import com.dsomorov.email.mappers.StatusMapper;
import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.models.dtos.RecipientDto;
import com.dsomorov.email.models.dtos.StatusDto;
import com.dsomorov.email.models.dtos.UpdateEmailDto;
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
      Chain chain = chainRepository.save(
        Chain.builder()
             .subject(emailDto.getSubject())
             .build()
      );
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
  public List<EmailDto> findEmailChainByUserId(Long userId, Long chainId)
  {
    return emailRepository
      .findEmailChainByUserId(userId, chainId)
      .stream()
      .map(emailMapper::mapTo)
      .toList();
  }
  
  @Transactional
  public EmailDto updateEmail(Long id, UpdateEmailDto updateEmailDto)
  {
    Email savedEmail = emailRepository
      .findById(id)
      .map(foundEmail -> {
        Optional.ofNullable(updateEmailDto.getBody()).ifPresent(foundEmail::setBody);
        Optional.ofNullable(updateEmailDto.getIsDraft()).ifPresent(foundEmail::setIsDraft);
        foundEmail.setDate(Date.from(Clock.systemUTC().instant()));
        return emailRepository.save(foundEmail);
      })
      .orElseThrow(() -> new RuntimeException("Email does not exist"));
    
    if (updateEmailDto.getAddedRecipients() != null)
    {
      this._saveEmailRecipients(
        updateEmailDto.getAddedRecipients(),
        savedEmail
      );
    }
    if (updateEmailDto.getRemovedRecipients() != null)
    {
      this._deleteRemovedRecipients(
        updateEmailDto.getRemovedRecipients(),
        savedEmail
      );
    }
    
    List<Recipient> foundRecipients = recipientRepository.findAllByEmail(savedEmail);
    
    if (!savedEmail.getIsDraft())
    {
      if (foundRecipients.isEmpty())
      {
        throw new RuntimeValidationException("Cannot send email with all recipients removed");
      }
      else
      {
        this._createStatuses(foundRecipients, savedEmail);
      }
    }
    
    savedEmail.setRecipients(foundRecipients);
    return emailMapper.mapTo(savedEmail);
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
      .map(recipientDto -> {
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
      })
      .toList();
  }
  
  private void _deleteRemovedRecipients(List<RecipientDto> removedRecipients, Email email)
  {
    ArrayList<Long> idsToRemove = new ArrayList<>();
    
    removedRecipients.forEach(recipientDto -> {
      Optional<Address> foundAddress = addressRepository.findByUsernameAndServer(
        recipientDto.getUsername(),
        recipientDto.getServer()
      );
      
      if (foundAddress.isEmpty()) {return;}
      
      recipientRepository
        .findByEmailAndAddress(email, foundAddress.get())
        .ifPresent(recipient -> idsToRemove.add(recipient.getId()));
    });
    
    recipientRepository.deleteAllById(idsToRemove);
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
    
    statusRepository
      .findByAddressAndChainAndLocation(
        email.getSenderAddress(),
        email.getChain(),
        Location.INBOX
      ).ifPresent(status -> {
        status.setEmail(email);
        status.setRead(true);
      });
  }
}

