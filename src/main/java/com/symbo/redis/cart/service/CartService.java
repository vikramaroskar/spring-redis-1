package com.symbo.redis.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;
import com.symbo.redis.books.domain.Book;
import com.symbo.redis.books.repo.BookRepository;
import com.symbo.redis.cart.domain.Cart;
import com.symbo.redis.cart.domain.CartItem;
import com.symbo.redis.cart.repo.CartRepository;
import com.symbo.redis.users.domain.User;
import com.symbo.redis.users.repo.UserRepository;

@Service
public class CartService {

 @Autowired
 private CartRepository cartRepository;

 @Autowired
 private BookRepository bookRepository;

 @Autowired
 private UserRepository userRepository;

 private JReJSON redisJson = new JReJSON();

 Path cartItemsPath = Path.of(".cartItems");

 public Cart get(String id) {
   return cartRepository.findById(id).get();
 }

 public void addToCart(String id, CartItem item) {
   Optional<Book> book = bookRepository.findById(item.getIsbn());
   if (book.isPresent()) {
     String cartKey = CartRepository.getKey(id);
     item.setPrice(book.get().getPrice());
     redisJson.arrAppend(cartKey, cartItemsPath, item);
   }
 }

 public void removeFromCart(String id, String isbn) {
   Optional<Cart> cartFinder = cartRepository.findById(id);
   if (cartFinder.isPresent()) {
     Cart cart = cartFinder.get();
     String cartKey = CartRepository.getKey(cart.getId());
     List<CartItem> cartItems = new ArrayList<CartItem>(cart.getCartItems());
     OptionalLong cartItemIndex =  LongStream.range(0, cartItems.size()).filter(i -> cartItems.get((int) i).getIsbn().equals(isbn)).findFirst();
     if (cartItemIndex.isPresent()) {
       redisJson.arrPop(cartKey, CartItem.class, cartItemsPath, cartItemIndex.getAsLong());
     }
   }
 }

 public void checkout(String id) {
   Cart cart = cartRepository.findById(id).get();
   User user = userRepository.findById(cart.getUserId()).get();
   cart.getCartItems().forEach(cartItem -> {
     Book book = bookRepository.findById(cartItem.getIsbn()).get();
     user.addBook(book);
   });
   userRepository.save(user);
   // cartRepository.delete(cart);
 }
}
