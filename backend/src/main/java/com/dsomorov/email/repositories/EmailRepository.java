package com.dsomorov.email.repositories;

import com.dsomorov.email.models.entities.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmailRepository extends CrudRepository<Email, Long>
{
  List<Email> findAllBySenderIdAndIsDraft(Long senderId, Boolean isDraft);
}
