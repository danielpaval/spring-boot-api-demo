package com.example.demo.entity;

import com.example.common.entity.AbstractAutoIncrementCommonEntity;
import com.example.common.entity.BooleanZeroOneConverter;
import com.example.common.entity.DeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "users")
@Audited
public class User extends AbstractAutoIncrementCommonEntity implements DeletableEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deleted", nullable = false)
    @Convert(converter = BooleanZeroOneConverter.class)
    @Builder.Default
    private boolean deleted = false;

    /*@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.Builder.Default
    private Set<Enrollment> enrollments = new HashSet<>();*/

}