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
public class UserDto
{
  private Long id;
  
  private String username;
  
  private String server;
  
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
}
