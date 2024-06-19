package com.dsomorov.email.repositories;

import com.dsomorov.email.models.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
}
