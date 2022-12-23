package com.symbo.redis.books.repo;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.symbo.redis.books.domain.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, String> {
}