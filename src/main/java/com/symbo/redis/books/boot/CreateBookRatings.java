package com.symbo.redis.books.boot;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.symbo.redis.books.domain.Book;
import com.symbo.redis.books.domain.BookRating;
import com.symbo.redis.books.repo.BookRatingRepository;
import com.symbo.redis.users.domain.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(4)
@Slf4j
public class CreateBookRatings implements CommandLineRunner {

  @Value("${app.numberOfRatings}")
  private Integer numberOfRatings;

  @Value("${app.ratingStars}")
  private Integer ratingStars;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private BookRatingRepository bookRatingRepo;

  @Override
  public void run(String... args) throws Exception {
    if (bookRatingRepo.count() == 0) {
    Random random = new Random();
    IntStream.range(0, numberOfRatings).forEach(n -> {
          String bookId = redisTemplate.opsForSet().randomMember(Book.class.getName());
          String userId = redisTemplate.opsForSet().randomMember(User.class.getName());
          int stars = random.nextInt(ratingStars) + 1;

          User user = new User();
          user.setId(userId);

          Book book = new Book();
          book.setId(bookId);

          BookRating rating = BookRating.builder() //
            .user(user) //
            .book(book) //
            .rating(stars).build();
          bookRatingRepo.save(rating);
    });

    //log.info(">>>> BookRating created...");
    }
  }
}
