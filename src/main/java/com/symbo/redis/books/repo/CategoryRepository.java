package com.symbo.redis.books.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.symbo.redis.books.domain.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {
}
