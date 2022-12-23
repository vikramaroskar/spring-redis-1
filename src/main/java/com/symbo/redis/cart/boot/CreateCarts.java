package com.symbo.redis.cart.boot;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.symbo.redis.books.domain.Book;
import com.symbo.redis.books.repo.BookRepository;
import com.symbo.redis.cart.domain.Cart;
import com.symbo.redis.cart.domain.CartItem;
import com.symbo.redis.cart.repo.CartRepository;
import com.symbo.redis.cart.service.CartService;
import com.symbo.redis.users.domain.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(5)
@Slf4j
public class CreateCarts implements CommandLineRunner {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  BookRepository bookRepository;

  @Autowired
  CartService cartService;

  @Value("${app.numberOfCarts}")
  private Integer numberOfCarts;

  @Override
  public void run(String... args) throws Exception {
    if (cartRepository.count() == 0) {
      Random random = new Random();

      // loops for the number of carts to create
      IntStream.range(0, numberOfCarts).forEach(n -> {
        // get a random user
        String userId = redisTemplate.opsForSet()//
            .randomMember(User.class.getName());

        // make a cart for the user
        Cart cart = Cart.builder()//
            .userId(userId) //
            .build();

        // get between 1 and 7 books
        Set<Book> books = getRandomBooks(bookRepository, 7);

        // add to cart
        cart.setCartItems(getCartItemsForBooks(books));

        // save the cart
        cartRepository.save(cart);

        // randomly checkout carts
        if (random.nextBoolean()) {
          cartService.checkout(cart.getId());
        }
      });

      //log.info(">>>> Created Carts...");
    }
  }

  private Set<Book> getRandomBooks(BookRepository bookRepository, int max) {
    Random random = new Random();
    int howMany = random.nextInt(max) + 1;
    Set<Book> books = new HashSet<Book>();
    IntStream.range(1, howMany).forEach(n -> {
      String randomBookId = redisTemplate.opsForSet().randomMember(Book.class.getName());
      books.add(bookRepository.findById(randomBookId).get());
    });

    return books;
  }

  private Set<CartItem> getCartItemsForBooks(Set<Book> books) {
    Set<CartItem> items = new HashSet<CartItem>();
    books.forEach(book -> {
      CartItem item = CartItem.builder()//
          .isbn(book.getId()) //
          .price(book.getPrice()) //
          .quantity(1L) //
          .build();
      items.add(item);
    });

    return items;
  }
}
