package com.dsomorov.email.models.dtos;

import com.dsomorov.email.validation.ValidateEmailRecipients;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ValidateEmailRecipients
public class EmailDto
{
  private Long id;
  
  private Long chainId;
  
  @NotNull
  private Boolean isDraft;
  
  @Size(max = 50)
  private String subject;
  
  private String body;
  
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull(message = "Email must have a sender")
  @Positive()
  private Long senderId;
  
  private UserDto sender;
  
  @Valid
  private List<RecipientDto> recipients;
  
  public EmailDto asSummary()
  {
    return EmailDto
      .builder()
      .id(id)
      .chainId(chainId)
      .subject(subject)
      .senderId(senderId)
      .body(body.substring(0, Math.min(body.length(), 50)))
      .build();
  }
}
