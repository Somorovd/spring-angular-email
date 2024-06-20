package com.dsomorov.email.controllers;

import com.dsomorov.email.models.dtos.EmailDto;
import com.dsomorov.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/emails")
public class EmailController
{
  @Autowired
  private EmailService emailService;
  
  @PostMapping()
  public ResponseEntity<EmailDto> createEmail(@RequestBody EmailDto emailDto)
  {
    EmailDto savedEmailDto = emailService.createEmail(emailDto);
    return new ResponseEntity<>(savedEmailDto, HttpStatus.OK);
  }
}
