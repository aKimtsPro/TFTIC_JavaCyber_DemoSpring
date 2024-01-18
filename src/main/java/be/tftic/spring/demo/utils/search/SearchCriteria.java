package be.tftic.spring.demo.utils.search;

import org.springframework.data.jpa.domain.Specification;

public interface SearchCriteria<T> {
    Specification<T> toSpecification();
}
