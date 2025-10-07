package com.example.demo.mapper;

import com.example.common.mapper.CommonMapper;
import com.example.common.mapper.JsonNullableMapper;
import com.example.demo.dto.UserPatchDto;
import com.example.demo.entity.User;
import com.example.demo.generated.dto.UserDto;
import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = JsonNullableMapper.class)
public abstract class UserMapper implements CommonMapper<Long, User, UserDto, UserPatchDto> {

    @Autowired
    private EntityManager entityManager;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void patch(UserPatchDto userPatchDto, @MappingTarget User user) {
        userPatchDto.getName().ifPresent(user::setName);
    }

    @Named("userIdToUser")
    public User map(Long userId) {
        if (userId == null) {
            return null;
        }
        return entityManager.getReference(User.class, userId);
    }

}