package com.symbo.redis.cart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.symbo.redis.cart.domain.Cart;
import com.symbo.redis.cart.domain.CartItem;
import com.symbo.redis.cart.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

  @Autowired
  private CartService cartService;

  @GetMapping("/{id}")
  public Cart get(@PathVariable("id") String id) {
    return cartService.get(id);
  }

  @PostMapping("/{id}")
  public void addToCart(@PathVariable("id") String id, @RequestBody CartItem item) {
    cartService.addToCart(id, item);
  }

  @DeleteMapping("/{id}")
  public void removeFromCart(@PathVariable("id") String id, @RequestBody String isbn) {
    cartService.removeFromCart(id, isbn);
  }

  @PostMapping("/{id}/checkout")
  public void checkout(@PathVariable("id") String id) {
    cartService.checkout(id);
  }

}
