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
@Table(name = "addresses")
public class Address
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
  private Long id;
  
  private String username;
  
  private String server;
}
