package com.symbo.redis.books.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.symbo.redis.books.domain.BookRating;

@Repository
public interface BookRatingRepository extends CrudRepository<BookRating, String> {
}