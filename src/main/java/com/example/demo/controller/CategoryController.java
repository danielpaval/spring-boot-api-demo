package com.example.demo.controller;

import com.example.demo.generated.api.CategoriesApi;
import com.example.demo.generated.dto.CategoryDto;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoriesApi {

    private final CategoryService categoryService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> saveCategory(CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @Override
    public ResponseEntity<CategoryDto> findCategoryById(String id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<CategoryDto> updateCategory(String id, CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.update(id, categoryDto));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(String id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
