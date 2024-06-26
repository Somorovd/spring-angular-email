package com.dsomorov.email.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long id;
  
  @NotNull
  @Size(min = 1, max = 64, message = "Username must be between 1 and 64 characters")
  private String username;
  
  @NotNull
  @Size(min = 1, max = 254, message = "Server must be between 1 and 254 characters")
  @Pattern(regexp = ".*\\..*", message = "Server not valid format <A>.<B>")
  private String server;
}
