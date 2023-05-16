package dev.fernando.agileblog.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ContainsIgnoreCase<T> implements Specification<T> {
    private final String property;
    private final String value;

    public ContainsIgnoreCase(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(root.get(property)),
                "%" + value.toLowerCase() + "%"
        );
    }
}