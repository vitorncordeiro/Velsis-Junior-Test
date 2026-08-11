package com.example.versis.specification;

import com.example.versis.dto.request.UserFilter;
import com.example.versis.model.UserModel;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserModel> withFilters(UserFilter filter){
        return (root, query, criteriaBuilder) -> {
            if (filter.name() != null && !filter.name().isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + filter.name().toLowerCase() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }
}
