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
@Table(name = "statuses")
public class Status
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_id_seq")
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  
  @ManyToOne
  @JoinColumn(name = "chain_id")
  private Chain chain;
  
  private boolean isRead;
  
  private boolean isStarred;
}
