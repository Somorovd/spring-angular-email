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
      "where u.id = ?1 and e.is_draft = true",
    nativeQuery = true)
  List<Email> findDraftEmailsByUserId(Long userId);
  
  @Query(
    value = "" +
      "select e.* from emails e join users u on u.address_id = e.sender_address_id " +
      "where u.id = ?1 and e.is_draft = false",
    nativeQuery = true)
  List<Email> findSentEmailsByUserId(Long userId);
  
  @Query(
    value =
      "" +
        "select * from ( " +
        "select e.*, " +
        "ROW_NUMBER() OVER (PARTITION BY e.chain_id ORDER BY e.date DESC) as rn " +
        "from emails e " +
        "join recipients r on r.email_id = e.id " +
        "join users u_send on u_send.address_id = e.sender_address_id " +
        "join users u_rec on u_rec.address_id = r.address_id " +
        "where (u_rec.id = ?1 and e.is_draft = false) or u_send.id = ?1 " +
        ") where rn = 1",
    nativeQuery = true
  )
  List<Email> findLatestEmailsInChainsByUserId(Long userId);
  
  @Query(
    value =
      "" +
        "select distinct e.* from emails e " +
        "join recipients r on r.email_id = e.id " +
        "join users u_send on u_send.address_id = e.sender_address_id " +
        "join users u_rec on u_rec.address_id = r.address_id " +
        "where e.chain_id = ?2 and (u_send.id = ?1 or (u_rec.id = ?1 and e.is_draft = false)) " +
        "order by e.\"date\" ",
    nativeQuery = true
  )
  List<Email> findEmailChainByUserId(Long userId, Long chainId);
}