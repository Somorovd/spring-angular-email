package com.dsomorov.email.repositories;

import com.dsomorov.email.models.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends CrudRepository<Address, Long>
{
}
