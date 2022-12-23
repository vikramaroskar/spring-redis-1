package com.symbo.redis.users.boot;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.symbo.redis.users.domain.Role;
import com.symbo.redis.users.repo.RoleRepository;

@Component
@Order(1)
public class CreateRoles implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		if (roleRepository.count() == 0) {
			Role adminRole = Role.builder().name("admin").build();
			Role customerRole = Role.builder().name("customer").build();
			roleRepository.save(adminRole);
			roleRepository.save(customerRole);
			//log.info(">>>> Created admin and customer roles...");
		}
	}
}
