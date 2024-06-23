package com.dsomorov.email.repositories;

import com.dsomorov.email.models.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AddressRepository extends CrudRepository<Address, Long>
{
  Optional<Address> findByUsernameAndServer(String username, String server);
}
