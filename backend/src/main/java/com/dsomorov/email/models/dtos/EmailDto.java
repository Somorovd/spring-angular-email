package com.dsomorov.email.models.dtos;

import com.dsomorov.email.validation.ValidateEmailRecipients;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
  
  private Date date;
  
  @NotNull(message = "Email must include sender address")
  @Valid
  private AddressDto sender;
  
  @Valid
  private List<RecipientDto> recipients;
  
  public EmailDto asSummary()
  {
    return EmailDto
      .builder()
      .id(id)
      .chainId(chainId)
      .sender(sender)
      .subject(subject)
      .body(body.substring(0, Math.min(body.length(), 50)))
      .date(date)
      .build();
  }
}
