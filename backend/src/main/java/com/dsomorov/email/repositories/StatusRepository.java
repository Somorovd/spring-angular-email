package com.dsomorov.email.repositories;

import com.dsomorov.email.enums.Location;
import com.dsomorov.email.models.entities.Address;
import com.dsomorov.email.models.entities.Chain;
import com.dsomorov.email.models.entities.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StatusRepository extends CrudRepository<Status, Long>
{
  Optional<Status> findByAddressAndChainAndLocation(Address address, Chain chain, Location location);
  
  @Query(
    value = "" +
      "select s.* from statuses s " +
      "join users u on u.address_id = s.address_id " +
      "join emails e on s.email_id = e.id " +
      "where u.id = ?1 and s.location = 'INBOX'",
    nativeQuery = true
  )
  List<Status> findUserInbox(Long userId);
}
