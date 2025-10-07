package com.example.demo.service.security;

import com.example.demo.security.SecurityUtils;

public interface AuthorizationService {

    boolean isCurrentUser(Long userId);

    boolean isCourseTeacher(Long userId, Long courseId);

    default boolean isCourseTeacher(Long courseId) {
        Long userId = SecurityUtils.getUserId();
        return userId != null && isCourseTeacher(userId, courseId);
    }

    boolean isCourseStudent(Long userId, Long courseId);

    default boolean isCourseStudent(Long courseId) {
        Long userId = SecurityUtils.getUserId();
        return userId != null && isCourseStudent(userId, courseId);
    }

    default boolean isCourseUser(Long userId, Long courseId) {
        return isCourseTeacher(userId, courseId) || isCourseStudent(userId, courseId);
    }

    default boolean isCourseUser(Long courseId) {
        Long userId = SecurityUtils.getUserId();
        return userId != null && isCourseUser(userId, courseId);
    }

}
