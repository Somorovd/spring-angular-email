package com.dsomorov.email.models.dtos;

import com.dsomorov.email.validation.ValidateEmailRecipients;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidateEmailRecipients
public class StatusDto
{
  private Long id;
  
  private Long chainId;
  
  private Long userId;
  
  private boolean isRead;
  
  private boolean isStarred;
  
  private EmailDto email;
}
