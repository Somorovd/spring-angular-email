package com.dsomorov.email.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDto
{
  private Long id;
  
  private String subject;
  
  private String body;
  
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long senderId;
  
  private UserDto sender;
  
  private Long chainId;
}
