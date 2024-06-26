package com.dsomorov.email.models.entities;

import com.dsomorov.email.enums.Location;
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
  @JoinColumn(name = "address_id")
  private Address address;
  
  @ManyToOne
  @JoinColumn(name = "chain_id")
  private Chain chain;
  
  @ManyToOne
  @JoinColumn(name = "email_id")
  private Email email;
  
  private boolean isRead;
  
  private boolean isStarred;
  
  @Enumerated(EnumType.STRING)
  private Location location;
}
