package com.example.demo.mapper;

import com.example.common.mapper.CommonMapper;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.EnrollmentId;
import com.example.demo.generated.dto.EnrollmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, UserMapper.class})
public interface EnrollmentMapper extends CommonMapper<EnrollmentId, Enrollment, EnrollmentDto, Void> {

    EnrollmentDto map(Enrollment enrollment);

    @Mapping(source = "id.courseId", target = "course", qualifiedByName = "courseIdToCourse")
    @Mapping(source = "id.studentId", target = "student", qualifiedByName = "userIdToUser")
    void update(EnrollmentDto enrollmentDto, @MappingTarget Enrollment enrollment);

}
