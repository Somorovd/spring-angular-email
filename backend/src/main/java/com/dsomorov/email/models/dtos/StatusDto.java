package com.dsomorov.email.models.dtos;

import com.dsomorov.email.enums.Location;
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
  
  private Boolean isRead;
  
  private Boolean isStarred;
  
  private Location location;
  
  private EmailDto email;
}
