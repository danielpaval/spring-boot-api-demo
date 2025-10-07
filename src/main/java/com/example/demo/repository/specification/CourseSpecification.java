package com.example.demo.repository.specification;

import com.example.demo.entity.Course;
import com.example.demo.entity.Course_;
import com.example.demo.entity.User_;
import com.example.demo.service.criteria.LegacyCourseCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.repository.specification.SpecificationUtils.*;

@ToString
@RequiredArgsConstructor
public class CourseSpecification implements Specification<Course> {

    private final LegacyCourseCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!criteria.getIds().isEmpty()) {
            predicates.add(createInPredicate(criteriaBuilder, root.get(Course_.ID), criteria.getIds()));
        }

        addStringPredicate(predicates, criteriaBuilder, root.get(Course_.NAME), criteria.getName(), true);

        addEqualityPredicate(predicates, criteriaBuilder, root.get(Course_.UPDATED_BY).get(User_.ID), criteria.getUpdatedById());
        addEqualityPredicate(predicates, criteriaBuilder, root.get(Course_.CREATED_BY).get(User_.ID), criteria.getCreatedById());

        addDateRangePredicate(predicates, criteriaBuilder, root.get(Course_.START_DATE), null, criteria.getStartDate(), null);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

}
