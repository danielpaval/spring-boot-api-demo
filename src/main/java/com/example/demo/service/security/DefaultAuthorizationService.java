package com.example.demo.service.security;

import com.example.demo.entity.EnrollmentId;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("authorizationService")
@RequiredArgsConstructor
public class DefaultAuthorizationService implements AuthorizationService {

    private final CourseRepository courseRepository;

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public boolean isCurrentUser(Long userId) {
        Long currentUserId = SecurityUtils.getUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }

    @Override
    public boolean isCourseTeacher(Long userId, Long courseId) {
        if (userId == null || courseId == null) {
            return false;
        }
        return courseRepository.findById(courseId)
                .map(course -> course.getTeacher() != null && userId.equals(course.getTeacher().getId()))
                .orElse(false);
    }

    @Override
    public boolean isCourseStudent(Long userId, Long courseId) {
        if (userId == null || courseId == null) {
            return false;
        }
        EnrollmentId enrollmentId = new EnrollmentId(courseId, userId);
        return enrollmentRepository.existsById(enrollmentId);
    }

}
