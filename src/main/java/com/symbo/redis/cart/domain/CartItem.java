package com.symbo.redis.cart.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {
  private String isbn;
  private Double price;
  private Long quantity;
}