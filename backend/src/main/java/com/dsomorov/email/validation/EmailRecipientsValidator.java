package com.dsomorov.email.validation;

import com.dsomorov.email.models.dtos.EmailDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailRecipientsValidator implements ConstraintValidator<ValidateEmailRecipients, EmailDto>
{
  
  private String message;
  
  @Override
  public void initialize(ValidateEmailRecipients constraintAnnotation)
  {
    this.message = constraintAnnotation.message();
  }
  
  @Override
  public boolean isValid(EmailDto emailDto, ConstraintValidatorContext context)
  {
    if (!emailDto.getIsDraft() && emailDto.getRecipients().isEmpty())
    {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(this.message)
             .addPropertyNode("recipients")
             .addConstraintViolation();
      return false;
    }
    return true;
  }
}
