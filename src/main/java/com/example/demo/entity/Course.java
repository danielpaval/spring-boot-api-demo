package com.example.demo.entity;

import com.example.common.entity.AbstractAutoIncrementCommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "courses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Course extends AbstractAutoIncrementCommonEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "teacher", nullable = false)
    private User teacher;

    @CreatedBy
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    /*@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.Builder.Default
    private Set<Enrollment> enrollments = new HashSet<>();*/

}