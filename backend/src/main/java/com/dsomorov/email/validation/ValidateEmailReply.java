package com.dsomorov.email.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = EmailReplyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateEmailReply
{
  String message() default "Email with ChainId should not have a subject";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}