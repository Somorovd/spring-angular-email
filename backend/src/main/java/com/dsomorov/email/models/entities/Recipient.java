package com.dsomorov.email.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipients")
public class Recipient
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipient_id_seq")
  private Long id;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  private Address address;
  
  @ManyToOne
  @JoinColumn(name = "email_id")
  private Email email;
}
