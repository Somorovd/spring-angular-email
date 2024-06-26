package com.dsomorov.email.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class UpdateEmailDto
{
  private Long id;
  
  @NotNull
  private Boolean isDraft;
  
  @Size(max = 50)
  private String subject;
  
  private String body;
  
  @Valid
  private List<RecipientDto> addedRecipients;
  
  @Valid
  private List<RecipientDto> removedRecipients;
}
