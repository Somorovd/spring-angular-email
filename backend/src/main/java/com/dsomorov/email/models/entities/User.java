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
@Table(name = "users")
public class User
{
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
  private Long id;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  private Address address;
  
}