package com.dsomorov.email.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = EmailRecipientsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateEmailRecipients
{
  String message() default "Recipients list must contain at least one recipient if email is not a draft";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
