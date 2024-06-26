package com.dsomorov.email.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chains")
public class Chain
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chain_id_seq")
  private Long id;
  
  private String subject;
  
  @OneToMany(mappedBy = "chain", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Email> emails;
}
