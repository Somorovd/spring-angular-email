package com.dsomorov.email.models.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "emails")
public class Email
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_id_seq")
  private Long id;
  
  private Boolean isDraft;
  
  private String subject;
  
  @Lob
  @Column(columnDefinition = "TEXT")
  private String body;
  
  @ManyToOne
  @JoinColumn(name = "sender_address_id")
  private Address senderAddress;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date date;
  
  @ManyToOne
  @JoinColumn(name = "chain_id")
  private Chain chain;
  
  @OneToMany(mappedBy = "email", fetch = FetchType.EAGER)
  private List<Recipient> recipients;
}
