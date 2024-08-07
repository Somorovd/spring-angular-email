package com.dsomorov.email.controllers;

import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.models.dtos.StatusListResponseDto;
import com.dsomorov.email.models.dtos.UpdateEmailDto;
import com.dsomorov.email.services.ChainService;
import com.dsomorov.email.services.EmailService;
import com.dsomorov.email.services.UserService;
import com.dsomorov.email.validation.RuntimeValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/emails")
public class EmailController
{
  @Autowired
  private EmailService emailService;
  @Autowired
  private UserService  userService;
  @Autowired
  private ChainService chainService;
  
  @PostMapping()
  public ResponseEntity<EmailDto> createEmail(@Valid @RequestBody EmailDto emailDto)
  {
    EmailDto savedEmailDto = emailService.createEmail(emailDto);
    return new ResponseEntity<>(savedEmailDto, HttpStatus.OK);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<EmailDto> findEmailById(@PathVariable("id") Long id)
  {
    Optional<EmailDto> foundEmail = emailService.findEmailById(id);
    return foundEmail
      .map(emailDto -> new ResponseEntity<>(emailDto, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  @GetMapping("/user/{userId}/chain/{chainId}")
  public ResponseEntity<List<EmailDto>> findEmailChainByUserId
    (
      @PathVariable("userId") Long userId,
      @PathVariable("chainId") Long chainId
    )
  {
    if (!userService.existsById(userId))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (!chainService.existsById(chainId))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    List<EmailDto> foundEmailDtos = emailService.findEmailChainByUserId(userId, chainId);
    return new ResponseEntity<>(foundEmailDtos, HttpStatus.OK);
  }
  
  @GetMapping("/user/{userId}/sent")
  public ResponseEntity<List<EmailDto>> findSentEmailsByUserId
    (
      @PathVariable("userId") Long userId
    )
  {
    if (!userService.existsById(userId))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    List<EmailDto> foundEmailDtos = emailService.findSentEmailsByUserId(userId);
    return new ResponseEntity<>(foundEmailDtos, HttpStatus.OK);
  }
  
  @GetMapping("/user/{userId}/drafts")
  public ResponseEntity<List<EmailDto>> findUserDraftEmails
    (
      @PathVariable("userId") Long userId
    )
  {
    if (!userService.existsById(userId))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    List<EmailDto> foundEmailDtos = emailService.findDraftEmailsByUserId(userId);
    return new ResponseEntity<>(foundEmailDtos, HttpStatus.OK);
  }
  
  @GetMapping("/user/{userId}/inbox")
  public ResponseEntity<StatusListResponseDto> findUserInbox
    (
      @PathVariable("userId") Long userId
    )
  {
    if (!userService.existsById(userId))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    StatusListResponseDto statusListResponseDto = emailService.findUserInbox(userId);
    return new ResponseEntity<>(statusListResponseDto, HttpStatus.OK);
  }
  
  
  @PutMapping("/{id}")
  public ResponseEntity<EmailDto> updateEmail
    (
      @PathVariable("id") Long id,
      @Valid @RequestBody UpdateEmailDto updateEmailDto
    )
  {
    Optional<EmailDto> foundEmail = emailService.findEmailById(id);
    if (foundEmail.isEmpty())
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    else if (!foundEmail.get().getIsDraft())
    {
      throw new RuntimeValidationException("Cannot update email that has already been sent");
    }
    
    EmailDto savedEmailDto = emailService.updateEmail(id, updateEmailDto);
    return new ResponseEntity<>(savedEmailDto, HttpStatus.ACCEPTED);
  }
}
