package com.dsomorov.email.validation;

import com.dsomorov.email.models.dtos.EmailDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailReplyValidator implements ConstraintValidator<ValidateEmailReply, EmailDto>
{
  
  private String message;
  
  @Override
  public void initialize(ValidateEmailReply constraintAnnotation)
  {
    this.message = constraintAnnotation.message();
  }
  
  @Override
  public boolean isValid(EmailDto emailDto, ConstraintValidatorContext context)
  {
    if (emailDto.getChainId() != null && emailDto.getSubject() != null)
    {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(this.message)
             .addPropertyNode("subject")
             .addConstraintViolation();
      return false;
    }
    return true;
  }
}
