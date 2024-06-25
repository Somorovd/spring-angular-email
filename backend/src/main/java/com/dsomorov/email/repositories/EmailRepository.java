package com.dsomorov.email.repositories;

import com.dsomorov.email.models.entities.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmailRepository extends CrudRepository<Email, Long>
{
  @Query(
    value = "" +
      "select e.* from emails e join users u on u.address_id = e.sender_address_id " +
      "where u.id = ?1 and e.is_draft = true"
    , nativeQuery = true)
  List<Email> findDraftEmailsByUserId(Long userId);
  
  @Query(
    value = "" +
      "select e.* from emails e join users u on u.address_id = e.sender_address_id " +
      "where u.id = ?1 and e.is_draft = false"
    , nativeQuery = true)
  List<Email> findSentEmailsByUserId(Long userId);
}