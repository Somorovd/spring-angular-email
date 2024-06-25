package com.dsomorov.email.controllers;

import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.services.EmailService;
import com.dsomorov.email.services.UserService;
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
  
  @PutMapping("/{id}")
  public ResponseEntity<EmailDto> updateEmail
    (
      @PathVariable("id") Long id,
      @Valid @RequestBody EmailDto emailDto
    )
  {
    if (!emailService.existsById(id))
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    EmailDto updatedEmailDto = emailService.updateEmail(id, emailDto);
    return new ResponseEntity<>(updatedEmailDto, HttpStatus.ACCEPTED);
  }
}
