package com.dsomorov.email.repositories;

import com.dsomorov.email.models.entities.Chain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChainRepository extends CrudRepository<Chain, Long>
{
}
