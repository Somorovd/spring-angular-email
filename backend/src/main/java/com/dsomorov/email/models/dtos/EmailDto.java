package com.dsomorov.email.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
  
  @Size(max = 50)
  private String subject;
  
  private String body;
  
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull(message = "Email must have a sender")
  @Positive()
  private Long senderId;
  
  private UserDto sender;
  
  private Long chainId;
  
  public EmailDto asSummary()
  {
    return EmailDto.builder()
                   .id(id)
                   .subject(subject)
                   .senderId(senderId)
                   .chainId(chainId)
                   .body(body.substring(0, Math.min(body.length(), 50)))
                   .build();
  }
}
