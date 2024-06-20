package com.dsomorov.email.services;

import com.dsomorov.email.mappers.EmailMapper;
import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.models.entities.Chain;
import com.dsomorov.email.models.entities.Email;
import com.dsomorov.email.models.entities.User;
import com.dsomorov.email.repositories.ChainRepository;
import com.dsomorov.email.repositories.EmailRepository;
import com.dsomorov.email.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EmailService
{
  
  @Autowired
  private EmailRepository emailRepository;
  @Autowired
  private UserRepository  userRepository;
  @Autowired
  private ChainRepository chainRepository;
  @Autowired
  private EmailMapper     emailMapper;
  
  public EmailDto createEmail(EmailDto emailDto)
  {
    Optional<User> foundSender = userRepository.findById(emailDto.getSenderId());
    
    if (foundSender.isEmpty())
    {
      throw new RuntimeException("Email does not have a valid sender");
    }
    
    if (emailDto.getChainId() == null)
    {
      Chain chain = chainRepository.save(new Chain());
      emailDto.setChainId(chain.getId());
    }
    
    Email email = emailMapper.mapFrom(emailDto);
    email.setSender(foundSender.get());
    Email savedEmail = emailRepository.save(email);
    return emailMapper.mapTo(savedEmail);
  }
  
}
