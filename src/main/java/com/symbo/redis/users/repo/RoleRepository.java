package com.symbo.redis.users.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.symbo.redis.users.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
	
	Role findFirstByname(String role);
}
