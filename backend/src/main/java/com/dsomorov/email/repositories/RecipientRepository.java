package com.dsomorov.email.repositories;

import com.dsomorov.email.models.entities.Address;
import com.dsomorov.email.models.entities.Email;
import com.dsomorov.email.models.entities.Recipient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RecipientRepository extends CrudRepository<Recipient, Long>
{
  Optional<Recipient> findByEmailAndAddress(Email email, Address address);
}
