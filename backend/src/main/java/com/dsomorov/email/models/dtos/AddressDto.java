package com.dsomorov.email.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto
{
  private Long id;
  
  private String username;
  
  private String server;
}
