package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentId implements Serializable {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "student_id")
    private Long studentId;

}
