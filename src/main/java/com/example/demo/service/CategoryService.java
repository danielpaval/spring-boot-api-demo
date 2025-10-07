package com.example.demo.service;

import com.example.common.service.CommonService;
import com.example.demo.entity.Category;
import com.example.demo.generated.dto.CategoryDto;

public interface CategoryService extends CommonService<String, Category, CategoryDto, Void> {
}
