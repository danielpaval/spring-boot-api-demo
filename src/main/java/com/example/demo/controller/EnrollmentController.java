package com.example.demo.controller;

import com.example.demo.entity.EnrollmentId;
import com.example.demo.generated.api.EnrollmentsApi;
import com.example.demo.generated.dto.EnrollmentDto;
import com.example.demo.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollmentController implements EnrollmentsApi {

    private final EnrollmentService enrollmentService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentDto> saveEnrollment(EnrollmentDto enrollmentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.save(enrollmentDto));
    }

    @Override
    public ResponseEntity<EnrollmentDto> findEnrollmentById(Long courseId, Long studentId) {
        return enrollmentService.findById(new EnrollmentId(courseId, studentId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EnrollmentDto>> findAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.findAllBySpecification(Specification.anyOf()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEnrollmentById(Long courseId, Long studentId) {
        enrollmentService.deleteById(new EnrollmentId(courseId, studentId));
        return ResponseEntity.noContent().build();
    }

}
