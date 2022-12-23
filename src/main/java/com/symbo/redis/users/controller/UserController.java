package com.symbo.redis.users.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.symbo.redis.users.domain.User;
import com.symbo.redis.users.repo.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

//  @GetMapping
//  public Iterable<User> all() {
//    return userRepository.findAll();
//  }
//  
  @GetMapping
  public Iterable<User> all(@RequestParam(defaultValue = "") String email) {
    if (email.isEmpty()) {
      return userRepository.findAll();
    } else {
      Optional<User> user = Optional.ofNullable(userRepository.findFirstByEmail(email));
      List<User> lu = new ArrayList<User>();
      if (user.isPresent()) {
    	  lu.add(user.get());
          return lu;
       }
       return Collections.emptyList();
    }
  }
}
