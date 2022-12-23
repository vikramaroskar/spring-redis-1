package com.symbo.redis.books.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RedisHash
public class Category {
  @Id
  private String id;
  private String name;
}