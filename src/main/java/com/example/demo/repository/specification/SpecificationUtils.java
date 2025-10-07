package com.example.demo.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.time.ZonedDateTime;
import java.util.List;

public class SpecificationUtils {

    public static <T> void addEqualityPredicate(List<Predicate> predicates, CriteriaBuilder cb, Path<T> path, T value) {
        if (value != null) {
            predicates.add(cb.equal(path, value));
        }
    }

    public static <T> Predicate createInPredicate(CriteriaBuilder cb, Path<T> path, List<T> values) {
        CriteriaBuilder.In<T> inClause = cb.in(path);
        for (T value : values) {
            inClause.value(value);
        }
        return inClause;
    }

    public static void addStringPredicate(List<Predicate> predicates, CriteriaBuilder cb, Path<String> path, String value, boolean allowExactMatch) {
        if (value != null) {
            if (allowExactMatch && !value.contains("%")) {
                predicates.add(cb.equal(path, value));
            } else {
                predicates.add(cb.like(path, "%" + value + "%"));
            }
        }
    }

    public static void addDateRangePredicate(List<Predicate> predicates, CriteriaBuilder cb, Path<ZonedDateTime> startPath, Path<ZonedDateTime> endPath, ZonedDateTime start, ZonedDateTime end) {
        if (start != null && end != null) {
            predicates.add(cb.greaterThanOrEqualTo(startPath, start));
            predicates.add(cb.lessThan(endPath, end));
        } else if (start != null) {
            predicates.add(cb.equal(startPath, start));
        } else if (end != null) {
            predicates.add(cb.equal(endPath, end));
        }
    }

}
