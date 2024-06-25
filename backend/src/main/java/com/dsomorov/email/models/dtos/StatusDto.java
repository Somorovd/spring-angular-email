package com.dsomorov.email.models.dtos;

import com.dsomorov.email.validation.ValidateEmailRecipients;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long id;
  
  private Long chainId;
  
  private Long userId;
  
  private boolean isRead;
  
  private boolean isStarred;
  
  private EmailDto email;
}
