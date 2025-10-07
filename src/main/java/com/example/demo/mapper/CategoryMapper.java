package com.example.demo.mapper;

import com.example.common.mapper.CommonMapper;
import com.example.common.mapper.JsonNullableMapper;
import com.example.demo.entity.Category;
import com.example.demo.generated.dto.CategoryDto;
import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = JsonNullableMapper.class)
public abstract class CategoryMapper implements CommonMapper<String, Category, CategoryDto, Void> {

    @Autowired
    private EntityManager entityManager;

    @Named("categoryIdToCategory")
    public Category map(String code) {
        if (code == null) {
            return null;
        }
        return entityManager.getReference(Category.class, code);
    }

    @Override
    @Mapping(target = "version", ignore = true)
    public abstract void update(CategoryDto dto, @MappingTarget Category entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "name", ignore = true)
    public abstract void patch(Void patchDto, @MappingTarget Category entity);
}
