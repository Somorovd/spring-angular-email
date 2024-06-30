package com.dsomorov.email.models.dtos;

import com.dsomorov.email.validation.ValidateEmailRecipients;
import com.dsomorov.email.validation.ValidateEmailReply;
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
@ValidateEmailReply
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

//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//  private StatusDto status;
  
  public EmailDto asSummary()
  {
    this.body = this.body.substring(0, 50);
    return this;
  }
}
