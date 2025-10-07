package com.example.demo.service.impl;

import com.example.common.service.AbstractCommonService;
import com.example.demo.entity.Category;
import com.example.demo.generated.dto.CategoryDto;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultCategoryService extends AbstractCommonService<String, Category, CategoryDto, Void> implements CategoryService {

    public DefaultCategoryService(CategoryRepository repository, CategoryMapper mapper, Validator validator) {
        super(repository, mapper, validator);
    }

}
