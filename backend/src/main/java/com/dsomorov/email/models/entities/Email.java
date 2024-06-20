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
@Table(name = "emails")
public class Email
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_id_seq")
  private Long id;
  
  private String subject;
  
  @Lob
  @Column(columnDefinition = "TEXT")
  private String body;
  
  @ManyToOne
  @JoinColumn(name = "chain_id")
  private Chain chain;
  
  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;
}
