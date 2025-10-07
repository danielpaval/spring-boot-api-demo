package com.example.demo.mapper;

import com.example.common.mapper.CommonMapper;
import com.example.common.mapper.JsonNullableMapper;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.CoursePatchDto;
import com.example.demo.entity.Course;
import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {JsonNullableMapper.class, UserMapper.class, CategoryMapper.class})
public abstract class CourseMapper implements CommonMapper<Long, Course, CourseDto, CoursePatchDto> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private EntityManager entityManager;

    @Named("courseIdToCourse")
    public Course map(Long courseId) {
        if (courseId == null) {
            return null;
        }
        return entityManager.getReference(Course.class, courseId);
    }

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    public abstract CourseDto map(Course course);

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "categoryIdToCategory")
    @Mapping(source = "teacherId", target = "teacher", qualifiedByName = "userIdToUser")
    @Mapping(source = "createdById", target = "createdBy", qualifiedByName = "userIdToUser")
    @Mapping(source = "updatedById", target = "updatedBy", qualifiedByName = "userIdToUser")
    public abstract void update(CourseDto courseDto, @MappingTarget Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void patch(CoursePatchDto coursePatchDto, @MappingTarget Course course) {
        coursePatchDto.getName().ifPresent(course::setName);
        coursePatchDto.getCategoryId().ifPresent(categoryId -> course.setCategory(categoryMapper.map(categoryId)));
        coursePatchDto.getStartDate().ifPresent(course::setStartDate);
        coursePatchDto.getTeacherId().ifPresent(userId -> course.setTeacher(userMapper.map(userId)));
    }

}