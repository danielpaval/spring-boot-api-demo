package com.example.demo.repository;

import com.example.common.repository.CommonRepository;
import com.example.demo.entity.Category;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CommonRepository<String, Category>, RevisionRepository<Category, String, Integer> {
}
