package com.symbo.redis.users.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.symbo.redis.users.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
  User findFirstByEmail(String email);
}
